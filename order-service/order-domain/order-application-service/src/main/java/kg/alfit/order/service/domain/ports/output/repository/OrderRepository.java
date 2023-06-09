package kg.alfit.order.service.domain.ports.output.repository;

import kg.alfit.domain.valueobject.OrderId;
import kg.alfit.order.service.domain.entity.Order;
import kg.alfit.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);

    Optional<Order> findById(OrderId orderId);


}
