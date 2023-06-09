package kg.alfit.order.service.messaging.listener.kafka;

import kg.alfit.kafka.consumer.KafkaConsumer;
import kg.alfit.kafka.order.avro.model.OrderApprovalStatus;
import kg.alfit.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import kg.alfit.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import kg.alfit.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static kg.alfit.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {
    private final RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
            topics = "${order-service.restaurant-approval-response-topic-name}")
    public void receive(@Payload List<RestaurantApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("""
                        {} message of restaurant approval responses received with keys {},
                        partitions {} and offsets {}
                        """, messages.size(), keys.toString(),
                partitions.toString(), offsets.toString());
        messages.forEach(restaurantApprovalRequestAvroModel -> {
            if (OrderApprovalStatus.APPROVED ==
                    restaurantApprovalRequestAvroModel.getOrderApprovalStatus()) {
                log.info("Processing approved order for order id: {}",
                        restaurantApprovalRequestAvroModel.getOrderId());
                restaurantApprovalResponseMessageListener.orderApproved(
                        orderMessagingDataMapper.
                                approvalResponseAvroModelToRestaurantApprovalResponse(restaurantApprovalRequestAvroModel)
                );
            }
            if (OrderApprovalStatus.REJECTED == restaurantApprovalRequestAvroModel.getOrderApprovalStatus()) {
                log.info("Processing rejected order for order id: {}, with failure messages {}",
                        restaurantApprovalRequestAvroModel.getOrderId(),
                       String.join(FAILURE_MESSAGE_DELIMITER, restaurantApprovalRequestAvroModel.getFailureMessages()));
                restaurantApprovalResponseMessageListener.orderRejected(
                        orderMessagingDataMapper
                                .approvalResponseAvroModelToRestaurantApprovalResponse(restaurantApprovalRequestAvroModel)
                );

            }
        });
    }

}
