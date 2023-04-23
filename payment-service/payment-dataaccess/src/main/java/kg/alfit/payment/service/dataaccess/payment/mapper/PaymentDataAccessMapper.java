package kg.alfit.payment.service.dataaccess.payment.mapper;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.domain.valueobject.Money;
import kg.alfit.domain.valueobject.OrderId;
import kg.alfit.payment.service.dataaccess.payment.entity.PaymentEntity;
import kg.alfit.payment.service.domain.entity.Payment;
import kg.alfit.payment.service.domain.valueobject.PaymentId;
import org.springframework.stereotype.Component;

@Component
public class PaymentDataAccessMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId().getValue())
                .customerId(payment.getCustomerId().getValue())
                .orderId(payment.getOrderId().getValue())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .createdAt(payment.getCreated())
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.Builder.builder()
                .paymentId(new PaymentId(paymentEntity.getId()))
                .customerId(new CustomerId(paymentEntity.getCustomerId()))
                .orderId(new OrderId(paymentEntity.getOrderId()))
                .price(new Money(paymentEntity.getPrice()))
                .created(paymentEntity.getCreatedAt())
                .build();
    }
}
