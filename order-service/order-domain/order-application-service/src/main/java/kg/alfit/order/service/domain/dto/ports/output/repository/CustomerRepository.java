package kg.alfit.order.service.domain.dto.ports.output.repository;

import kg.alfit.order.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findCustomer(UUID customerId);
}
