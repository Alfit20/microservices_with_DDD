package kg.alfit.order.service.domain;

import kg.alfit.order.service.domain.dto.track.request.TrackOrderQuery;
import kg.alfit.order.service.domain.dto.track.response.TrackOrderResponse;
import kg.alfit.order.service.domain.entity.Order;
import kg.alfit.order.service.domain.exception.OrderNotFoundException;
import kg.alfit.order.service.domain.mapper.OrderDataMapper;
import kg.alfit.order.service.domain.ports.output.repository.OrderRepository;
import kg.alfit.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTrackCommandHandler {
    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> orderResult = orderRepository.
                findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (orderResult.isEmpty()) {
            log.warn("Could not find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracking id " +
                    trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
