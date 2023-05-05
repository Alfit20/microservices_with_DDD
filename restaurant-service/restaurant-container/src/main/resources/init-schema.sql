DROP SCHEMA IF EXISTS restaurant CASCADE;

CREATE SCHEMA restaurant;

CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS restaurant.restaurants CASCADE;

CREATE TABLE restaurant.restaurants
(
    id     uuid                                           NOT NULL,
    name   character varying COLLATE pg_catalog."default" NOT NULL,
    active boolean                                        NOT NULL,
    CONSTRAINT restaurants_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS approval_status;

CREATE TYPE approval_status AS ENUM ('APPROVED', 'REJECTED');

DROP TABLE IF EXISTS restaurant.order_approval CASCADE;

CREATE TABLE restaurant.order_approval
(
    id            uuid            NOT NULL,
    restaurant_id uuid            NOT NULL,
    order_id      uuid            NOT NULL,
    status        approval_status NOT NULL,
    CONSTRAINT order_approval_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS restaurant.products CASCADE;

CREATE TABLE restaurant.products
(
    id        uuid                                           NOT NULL,
    name      character varying COLLATE pg_catalog."default" NOT NULL,
    price     numeric(10, 2)                                 NOT NULL,
    available boolean                                        NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS restaurant.restaurant_products CASCADE;

CREATE TABLE restaurant.restaurant_products
(
    id            uuid NOT NULL,
    restaurant_id uuid NOT NULL,
    product_id    uuid NOT NULL,
    CONSTRAINT restaurant_products_pkey PRIMARY KEY (id)
);

ALTER TABLE restaurant.restaurant_products
    ADD CONSTRAINT "FK_RESTAURANT_ID" FOREIGN KEY (restaurant_id)
        REFERENCES restaurant.restaurant_products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE restaurant.restaurant_products
    ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (product_id)
        REFERENCES restaurant.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
    NOT VALID;

DROP
MATERIALIZED VIEW IF EXISTS restaurant.order_restaurant_m_view;

CREATE
MATERIALIZED VIEW restaurant.order_restaurant_m_view
    TABLESPACE pg_default
AS
SELECT r.id        as restaurant_id,
       r.name      as restaurant_name,
       r.active    as restaurant_active,
       p.id        as product_id,
       p.name      as product_name,
       p.price     as product_price,
       p.available as product_available
FROM restaurant.restaurants r,
     restaurant.products p,
     restaurant.restaurant_products rp
where r.id = rp.restaurant_id
  and p.id = rd.product_id with data;

refresh
materialized view restaurant.order_restaurant_m_view;

DROP function IF EXISTS restaurant.refresh_order_restaurant_m_view;

CREATE
OR replace function restaurant.refresh_order_restaurant_m_view()
    returns trigger
    as '
    BEGIN
        refresh materialized VIEW restaurant.refresh_order_restaurant_m_view;
        return null;
    end;
    ' LANGUAGE plpqsql

DROP trigger IF EXISTS refresh_order_restaurant_m_view ON restaurant.restaurant_products;

CREATE trigger refresh_order_restaurant_m_view
    after INSERT OR
UPDATE OR
DELETE OR truncate
ON restaurant.restaurant_products FOR each statement
    EXECUTE PROCEDURE restaurant.refresh_order_restaurant_m_view();