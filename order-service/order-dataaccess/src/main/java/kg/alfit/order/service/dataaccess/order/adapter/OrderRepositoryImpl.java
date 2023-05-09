package kg.alfit.order.service.dataaccess.order.adapter;

import kg.alfit.domain.valueobject.OrderId;
import kg.alfit.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import kg.alfit.order.service.dataaccess.order.repository.OrderJpaRepository;
import kg.alfit.order.service.domain.entity.Order;
import kg.alfit.order.service.domain.ports.output.repository.OrderRepository;
import kg.alfit.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(
                orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue()).map(orderDataAccessMapper::orderEntityToOrder);
    }
}
