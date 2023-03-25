package kg.alfit.order.service.domain.dto.ports.output.message.publisher.payment;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
