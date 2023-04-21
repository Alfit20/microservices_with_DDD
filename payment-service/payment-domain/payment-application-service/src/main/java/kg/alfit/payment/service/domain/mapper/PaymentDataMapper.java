package kg.alfit.payment.service.domain.mapper;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.domain.valueobject.Money;
import kg.alfit.domain.valueobject.OrderId;
import kg.alfit.payment.service.domain.dto.PaymentRequest;
import kg.alfit.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {

    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.Builder.builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
