package kg.alfit.restaurant.service.messaging.mapper;

import kg.alfit.domain.valueobject.ProductId;
import kg.alfit.domain.valueobject.RestaurantOrderStatus;
import kg.alfit.kafka.order.avro.model.OrderApprovalStatus;
import kg.alfit.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import kg.alfit.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import kg.alfit.restaurant.service.domain.dto.RestaurantApprovalRequest;
import kg.alfit.restaurant.service.domain.entity.Product;
import kg.alfit.restaurant.service.domain.event.OrderApprovedEvent;
import kg.alfit.restaurant.service.domain.event.OrderRejectedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantMessagingDataMapper {
    public RestaurantApprovalResponseAvroModel
    orderApprovedEventToRestaurantApprovalResponseAvroModel(OrderApprovedEvent orderApprovedEvent) {
        return RestaurantApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(orderApprovedEvent.getOrderApproval().getOrderId().getValue().toString())
                .setRestaurantId(orderApprovedEvent.getRestaurantId().getValue().toString())
                .setCreateAt(orderApprovedEvent.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(
                        orderApprovedEvent.getOrderApproval().getOrderApprovalStatus().name()
                ))
                .setFailureMessages(orderApprovedEvent.getFailureMessages())
                .build();
    }

    public RestaurantApprovalResponseAvroModel
    orderRejectedEventToRestaurantApprovalResponseAvroModel(OrderRejectedEvent orderRejectedEvent) {
        return RestaurantApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(orderRejectedEvent.getOrderApproval().getOrderId().getValue().toString())
                .setRestaurantId(orderRejectedEvent.getRestaurantId().getValue().toString())
                .setCreateAt(orderRejectedEvent.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(
                        orderRejectedEvent.getOrderApproval().getOrderApprovalStatus().name()
                ))
                .setFailureMessages(orderRejectedEvent.getFailureMessages())
                .build();
    }


    public RestaurantApprovalRequest restaurantApprovalRequestAvroModelToRestaurantApproval(RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel) {
        return RestaurantApprovalRequest.builder()
                .id(restaurantApprovalRequestAvroModel.getId())
                .sagaId(restaurantApprovalRequestAvroModel.getSagaId())
                .restaurantId(restaurantApprovalRequestAvroModel.getRestaurantId())
                .orderId(restaurantApprovalRequestAvroModel.getOrderId())
                .restaurantOrderStatus(RestaurantOrderStatus.valueOf(
                        restaurantApprovalRequestAvroModel.getRestaurantOrderStatus().name()
                ))
                .products(restaurantApprovalRequestAvroModel.getProducts().stream()
                        .map(avroModel -> Product.builder()
                                .productId(new ProductId(UUID.fromString(avroModel.getId())))
                                .quantity(avroModel.getQuantity())
                                .build())
                        .collect(Collectors.toList())
                )
                .price(restaurantApprovalRequestAvroModel.getPrice())
                .createdAt(restaurantApprovalRequestAvroModel.getCreateAt())
                .build();
    }
}
