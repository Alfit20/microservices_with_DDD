package kg.alfit.order.service.dataaccess.customer.mapper;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.order.service.dataaccess.customer.entity.CustomerEntity;
import kg.alfit.order.service.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
