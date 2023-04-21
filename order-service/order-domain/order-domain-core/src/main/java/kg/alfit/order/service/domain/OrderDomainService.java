package kg.alfit.order.service.domain;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.order.service.domain.entity.Order;
import kg.alfit.order.service.domain.entity.Restaurant;
import kg.alfit.order.service.domain.event.OrderCancelledEvent;
import kg.alfit.order.service.domain.event.OrderCreatedEvent;
import kg.alfit.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);

    OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher);

    void cancelOrder(Order order, List<String> failureMessages);
}
