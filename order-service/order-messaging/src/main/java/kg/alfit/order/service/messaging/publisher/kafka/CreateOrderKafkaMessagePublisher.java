package kg.alfit.order.service.messaging.publisher.kafka;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.kafka.order.avro.model.PaymentRequestAvroModel;
import kg.alfit.kafka.producer.helper.KafkaMessageHelper;
import kg.alfit.kafka.producer.service.KafkaProducer;
import kg.alfit.order.service.domain.config.OrderServiceConfigData;
import kg.alfit.order.service.domain.event.OrderCreatedEvent;
import kg.alfit.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderKafkaMessagePublisher implements DomainEventPublisher<OrderCreatedEvent> {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;
    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCreatedEvent for order id: {}", orderId);
        try {
            PaymentRequestAvroModel paymentRequestAvroModel =
                    orderMessagingDataMapper.
                            orderCreatedEventToPaymentRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallback(
                                    orderServiceConfigData.
                                            getPaymentResponseTopicName(),
                            paymentRequestAvroModel,
                            orderId,
                            "PaymentRequestAvroModel")
            );

            log.info("PaymentRequestAvroModel sent to Kafka for order id: {}",
                    paymentRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("""
                    Error while sending PaymentRequestAvroModel message
                    to kafka with order id: {}, error: {}
                    """, orderId, e.getMessage());
        }

    }
}
