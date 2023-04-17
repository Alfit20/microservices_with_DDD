package kg.alfit.order.service.domain.mapper;

import kg.alfit.domain.valueobject.CustomerId;
import kg.alfit.domain.valueobject.Money;
import kg.alfit.domain.valueobject.ProductId;
import kg.alfit.domain.valueobject.RestaurantId;
import kg.alfit.order.service.domain.dto.create.request.CreateOrderCommand;
import kg.alfit.order.service.domain.dto.create.request.OrderAddress;
import kg.alfit.order.service.domain.dto.create.response.CreateOrderResponse;
import kg.alfit.order.service.domain.dto.track.response.TrackOrderResponse;
import kg.alfit.order.service.domain.entity.Order;
import kg.alfit.order.service.domain.entity.OrderItem;
import kg.alfit.order.service.domain.entity.Product;
import kg.alfit.order.service.domain.entity.Restaurant;
import kg.alfit.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.Builder.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(
                        createOrderCommand.getItems().stream()
                                .map(orderItem ->
                                        new Product(
                                                new ProductId(orderItem.getProduct()
                                                        .getId().getValue()))
                                )
                                .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.Builder.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .orderItems(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
       return TrackOrderResponse.builder()
               .orderTrackingId(order.getTrackingId().getValue())
               .orderStatus(order.getOrderStatus())
               .failureMessages(order.getFailureMessages())
               .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(
                UUID.randomUUID(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItem.builder()
                        .product(new Product(new ProductId(orderItem.getProduct().getId().getValue())))
                        .price(new Money(orderItem.getPrice().getAmount()))
                        .quantity(orderItem.getQuantity())
                        .subTotal(new Money(orderItem.getSubTotal().getAmount()))
                        .build())
                .collect(Collectors.toList());
    }


}
