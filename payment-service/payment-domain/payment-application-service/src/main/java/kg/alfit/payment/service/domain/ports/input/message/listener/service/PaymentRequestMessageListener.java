package kg.alfit.payment.service.domain.ports.input.message.listener.service;

import kg.alfit.payment.service.domain.dto.PaymentRequest;

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
