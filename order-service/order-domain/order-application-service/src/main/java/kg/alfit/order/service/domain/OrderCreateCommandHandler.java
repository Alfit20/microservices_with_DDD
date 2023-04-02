package kg.alfit.order.service.domain;

import kg.alfit.order.service.domain.dto.create.request.CreateOrderCommand;
import kg.alfit.order.service.domain.dto.create.response.CreateOrderResponse;
import kg.alfit.order.service.domain.event.OrderCreatedEvent;
import kg.alfit.order.service.domain.mapper.OrderDataMapper;
import kg.alfit.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateCommandHandler {
    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;


    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        applicationDomainEventPublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),
                "Order created successfully");
    }

}
