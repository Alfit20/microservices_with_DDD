package kg.alfit.restaurant.service.domain.mapper;

import kg.alfit.domain.valueobject.Money;
import kg.alfit.domain.valueobject.OrderId;
import kg.alfit.domain.valueobject.OrderStatus;
import kg.alfit.domain.valueobject.RestaurantId;
import kg.alfit.restaurant.service.domain.dto.RestaurantApprovalRequest;
import kg.alfit.restaurant.service.domain.entity.OrderDetail;
import kg.alfit.restaurant.service.domain.entity.Product;
import kg.alfit.restaurant.service.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataMapper {
    public Restaurant restaurantApprovalRequestToRestaurant(RestaurantApprovalRequest
                                                                             restaurantApprovalRequest) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(UUID.fromString(restaurantApprovalRequest.getRestaurantId())))
                .orderDetail(OrderDetail.builder()
                        .orderId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())))
                        .products(restaurantApprovalRequest.getProducts().stream().map(
                                product -> Product.builder()
                                        .productId(product.getId())
                                        .quantity(product.getQuantity())
                                        .build()
                        ).collect(Collectors.toList()))
                        .totalAmount(new Money(restaurantApprovalRequest.getPrice()))
                        .orderStatus(OrderStatus.valueOf(restaurantApprovalRequest.getRestaurantOrderStatus().name()))
                        .build())
                .build();
    }
}
