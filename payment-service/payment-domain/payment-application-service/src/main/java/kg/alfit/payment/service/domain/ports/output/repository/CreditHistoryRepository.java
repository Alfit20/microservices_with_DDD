package kg.alfit.payment.service.domain.ports.output.repository;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.payment.service.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository {

    CreditHistory save(CreditHistory creditHistory);

    Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);

}
