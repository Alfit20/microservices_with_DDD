package kg.alfit.order.service.domain.ports.input.message.listener.payment;

import kg.alfit.order.service.domain.dto.response.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
