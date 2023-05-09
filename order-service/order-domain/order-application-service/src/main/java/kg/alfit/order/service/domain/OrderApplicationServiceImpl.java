package kg.alfit.order.service.domain;

import kg.alfit.order.service.domain.dto.create.request.CreateOrderCommand;
import kg.alfit.order.service.domain.dto.create.response.CreateOrderResponse;
import kg.alfit.order.service.domain.dto.track.request.TrackOrderQuery;
import kg.alfit.order.service.domain.dto.track.response.TrackOrderResponse;
import kg.alfit.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;


    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
