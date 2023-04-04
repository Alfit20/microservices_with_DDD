package kg.alfit.order.service.domain.entity;

import kg.alfit.domain.entity.AggregateRoot;
import kg.alfit.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    public Customer() {}

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
