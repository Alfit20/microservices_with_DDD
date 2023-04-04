package kg.alfit.order.service.dataaccess.customer.adapter;

import kg.alfit.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import kg.alfit.order.service.dataaccess.customer.repository.CustomerJpaRepository;
import kg.alfit.order.service.domain.entity.Customer;
import kg.alfit.order.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
       return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
