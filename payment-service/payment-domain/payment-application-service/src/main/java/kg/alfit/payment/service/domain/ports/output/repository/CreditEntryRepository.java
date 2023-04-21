package kg.alfit.payment.service.domain.ports.output.repository;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.payment.service.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);

}
