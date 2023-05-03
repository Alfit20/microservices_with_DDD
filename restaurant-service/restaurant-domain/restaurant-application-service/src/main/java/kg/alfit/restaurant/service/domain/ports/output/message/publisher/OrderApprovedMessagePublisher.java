package kg.alfit.restaurant.service.domain.ports.output.message.publisher;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.restaurant.service.domain.event.OrderApprovedEvent;

public interface OrderApprovedMessagePublisher extends DomainEventPublisher<OrderApprovedEvent> {
}
