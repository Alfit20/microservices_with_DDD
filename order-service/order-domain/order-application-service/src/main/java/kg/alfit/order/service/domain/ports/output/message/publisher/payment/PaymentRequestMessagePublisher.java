package kg.alfit.order.service.domain.ports.output.message.publisher.payment;

import kg.alfit.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import kg.alfit.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {

    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
