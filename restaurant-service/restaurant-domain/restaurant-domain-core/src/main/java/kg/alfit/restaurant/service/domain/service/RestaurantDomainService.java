package kg.alfit.restaurant.service.domain.service;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.restaurant.service.domain.entity.Restaurant;
import kg.alfit.restaurant.service.domain.event.OrderApprovalEvent;
import kg.alfit.restaurant.service.domain.event.OrderApprovedEvent;
import kg.alfit.restaurant.service.domain.event.OrderRejectedEvent;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(
            Restaurant restaurant, List<String> failureMessages,
            DomainEventPublisher<OrderApprovedEvent>orderApprovedEventDomainEventPublisher,
            DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);

}
