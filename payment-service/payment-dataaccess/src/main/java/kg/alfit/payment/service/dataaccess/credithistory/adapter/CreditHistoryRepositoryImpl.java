package kg.alfit.payment.service.dataaccess.credithistory.adapter;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.payment.service.dataaccess.credithistory.entity.CreditHistoryEntity;
import kg.alfit.payment.service.dataaccess.credithistory.mapper.CreditHistoryDataAccessMapper;
import kg.alfit.payment.service.dataaccess.credithistory.repository.CreditHistoryJpaRepository;
import kg.alfit.payment.service.domain.entity.CreditHistory;
import kg.alfit.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {
    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(
                creditHistoryJpaRepository.save(creditHistoryDataAccessMapper
                        .creditHistoryToCreditHistoryEntity(creditHistory))
        );
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        Optional<List<CreditHistoryEntity>> creditHistory =
                creditHistoryJpaRepository.findByCustomerId(customerId.getValue());
        return creditHistory
                .map(creditHistoryEntities ->
                        creditHistoryEntities.stream()
                                .map(creditHistoryDataAccessMapper::creditHistoryEntityToCreditHistory)
                                .collect(Collectors.toList()));
    }
}
