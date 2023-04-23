package kg.alfit.payment.service.dataaccess.creditentry.mapper;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.domain.valueobject.Money;
import kg.alfit.payment.service.dataaccess.creditentry.entity.CreditEntryEntity;
import kg.alfit.payment.service.domain.entity.CreditEntry;
import kg.alfit.payment.service.domain.valueobject.CreditEntryId;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataAccessMapper {

    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntryEntity) {
        return CreditEntry.Builder.builder()
                .id(new CreditEntryId(creditEntryEntity.getId()))
                .customerId(new CustomerId(creditEntryEntity.getCustomerId()))
                .totalCreditAmount(new Money(creditEntryEntity.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getId().getValue())
                .customerId(creditEntry.getCustomerId().getValue())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }
}
