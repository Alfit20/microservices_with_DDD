package kg.alfit.payment.service.dataaccess.credithistory.mapper;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.domain.valueobject.Money;
import kg.alfit.payment.service.dataaccess.credithistory.entity.CreditHistoryEntity;
import kg.alfit.payment.service.domain.entity.CreditHistory;
import kg.alfit.payment.service.domain.valueobject.CreditHistoryId;
import org.springframework.stereotype.Component;

@Component
public class CreditHistoryDataAccessMapper {

    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistory) {
        return CreditHistory.Builder.builder()
                .creditHistoryId(new CreditHistoryId(creditHistory.getId()))
                .customerId(new CustomerId(creditHistory.getCustomerId()))
                .amount(new Money(creditHistory.getAmount()))
                .transactionType(creditHistory.getType())
                .build();
    }


    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        return CreditHistoryEntity.builder()
                .id(creditHistory.getId().getValue())
                .customerId(creditHistory.getCustomerId().getValue())
                .amount(creditHistory.getAmount().getAmount())
                .type(creditHistory.getTransactionType())
                .build();
    }
}
