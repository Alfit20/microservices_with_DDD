package kg.alfit.order.service.domain.dto.ports.output.message.publisher.payment;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
