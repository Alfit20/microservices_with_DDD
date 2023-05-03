package kg.alfit.restaurant.service.dataaccess.restaurant.mapper;

import kg.alfit.dataaccess.restaurant.entity.RestaurantEntity;
import kg.alfit.dataaccess.restaurant.exception.RestaurantDataAccessException;
import kg.alfit.domain.valueobject.Money;
import kg.alfit.domain.valueobject.OrderId;
import kg.alfit.domain.valueobject.ProductId;
import kg.alfit.domain.valueobject.RestaurantId;
import kg.alfit.restaurant.service.dataaccess.restaurant.entity.OrderApprovalEntity;
import kg.alfit.restaurant.service.domain.entity.OrderApproval;
import kg.alfit.restaurant.service.domain.entity.OrderDetail;
import kg.alfit.restaurant.service.domain.entity.Product;
import kg.alfit.restaurant.service.domain.entity.Restaurant;
import kg.alfit.restaurant.service.domain.valueobject.OrderApprovalId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {
    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getOrderDetail().getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("Restaurant could not be found!"));
        List<Product> restaurantProducts = restaurantEntities.stream()
                .map(entity -> Product.builder()
                        .productId(new ProductId(entity.getProductId()))
                        .name(entity.getProductName())
                        .price(new Money(entity.getProductPrice()))
                        .available(entity.getProductAvailable())
                        .build()).toList();

        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .orderDetail(OrderDetail.builder()
                        .products(restaurantProducts)
                        .build())
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getId().getValue())
                .restaurantId(orderApproval.getRestaurantId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .orderApprovalStatus(orderApproval.getOrderApprovalStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(orderApprovalEntity.getId()))
                .restaurantId(new RestaurantId(orderApprovalEntity.getRestaurantId()))
                .orderId(new OrderId(orderApprovalEntity.getRestaurantId()))
                .orderApprovalStatus(orderApprovalEntity.getOrderApprovalStatus())
                .build();
    }
}
