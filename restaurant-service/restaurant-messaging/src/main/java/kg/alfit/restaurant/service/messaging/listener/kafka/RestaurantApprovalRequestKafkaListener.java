package kg.alfit.restaurant.service.messaging.listener.kafka;

import kg.alfit.kafka.consumer.KafkaConsumer;
import kg.alfit.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import kg.alfit.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import kg.alfit.restaurant.service.messaging.mapper.RestaurantMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantApprovalRequestKafkaListener
        implements KafkaConsumer<RestaurantApprovalRequestAvroModel> {
    private final RestaurantApprovalRequestMessageListener restaurantApprovalRequestMessageListener;

    private final RestaurantMessagingDataMapper restaurantMessagingDataMapper;


    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
            topics = "${restaurant-service.restaurant-approval-request-topic-name}")
    public void receive(@Payload List<RestaurantApprovalRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("""
                        {} number of orders approval requests received with keys {},
                        partitions {} and offsets {}, sending for restaurant approval
                        """,
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString()
        );
        messages.forEach(restaurantApprovalRequestAvroModel -> {
            log.info("Processing order approval event at: {}", System.nanoTime());
            restaurantApprovalRequestMessageListener.approveOrder(
                    restaurantMessagingDataMapper.restaurantApprovalRequestAvroModelToRestaurantApproval(restaurantApprovalRequestAvroModel)
            );
        });
    }
}
