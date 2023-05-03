package kg.alfit.restaurant.service.domain.ports.output.message.publisher;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.restaurant.service.domain.event.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}
