package kg.alfit.order.service.messaging.publisher.kafka;

import kg.alfit.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import kg.alfit.kafka.producer.service.KafkaProducer;
import kg.alfit.order.service.domain.config.OrderServiceConfigData;
import kg.alfit.order.service.domain.event.OrderPaidEvent;
import kg.alfit.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import kg.alfit.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel
                    = orderMessagingDataMapper.
                    orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId, restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(
                            orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                            restaurantApprovalRequestAvroModel,
                            orderId,
                            "RestaurantApprovalRequestAvroModel"
                    ));
            log.info("RestaurantApprovalRequestAvroModel sent to kafka for " +
                    "order id: {}", orderId);
        } catch (Exception e) {
            log.error(
                    """
                    Error while sending RestaurantApprovalRequestAvroModel message
                    to kafka with order id: {}, error {} 
                    """, orderId, e.getMessage()
            );
        }


    }
}
