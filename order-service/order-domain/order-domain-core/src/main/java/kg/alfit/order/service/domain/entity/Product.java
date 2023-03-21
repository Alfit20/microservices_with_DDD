package kg.alfit.order.service.domain.entity;

import kg.alfit.domain.entity.BaseEntity;
import kg.alfit.domain.valueobject.Money;
import kg.alfit.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }


}
