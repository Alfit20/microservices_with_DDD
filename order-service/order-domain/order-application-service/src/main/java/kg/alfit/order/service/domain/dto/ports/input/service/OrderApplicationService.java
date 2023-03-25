package kg.alfit.order.service.domain.dto.ports.input.service;

import kg.alfit.order.service.domain.dto.dto.create.request.CreateOrderCommand;
import kg.alfit.order.service.domain.dto.dto.create.response.CreateOrderResponse;
import kg.alfit.order.service.domain.dto.dto.track.request.TrackOrderQuery;
import kg.alfit.order.service.domain.dto.dto.track.response.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);


}
