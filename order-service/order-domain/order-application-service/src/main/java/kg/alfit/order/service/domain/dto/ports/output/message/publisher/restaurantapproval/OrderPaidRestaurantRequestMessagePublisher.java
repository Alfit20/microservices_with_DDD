package kg.alfit.order.service.domain.dto.ports.output.message.publisher.restaurantapproval;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
