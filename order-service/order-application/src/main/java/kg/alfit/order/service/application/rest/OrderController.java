package kg.alfit.order.service.application.rest;

import kg.alfit.order.service.domain.dto.create.request.CreateOrderCommand;
import kg.alfit.order.service.domain.dto.create.response.CreateOrderResponse;
import kg.alfit.order.service.domain.dto.track.request.TrackOrderQuery;
import kg.alfit.order.service.domain.dto.track.response.TrackOrderResponse;
import kg.alfit.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;


    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand
                                                                   createOrderCommand) {
        log.info("Create order for customer: {} at restaurant: {}", createOrderCommand.getCustomerId(),
                createOrderCommand.getRestaurantId());
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created with tracking id: {}", createOrderResponse.getOrderTrackingId());
        return new ResponseEntity<>(createOrderResponse, HttpStatus.OK);
    }

    @GetMapping("{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId) {
        TrackOrderResponse trackOrderResponse = orderApplicationService.trackOrder(TrackOrderQuery.builder()
                .orderTrackingId(trackingId)
                .build());
        log.info("Returning order status with tracking id: {}", trackOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResponse);
    }


}
