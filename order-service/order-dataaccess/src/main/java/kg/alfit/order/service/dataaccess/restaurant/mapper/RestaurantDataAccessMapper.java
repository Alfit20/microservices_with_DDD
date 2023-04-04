package kg.alfit.order.service.dataaccess.restaurant.mapper;

import kg.alfit.domain.valueobject.Money;
import kg.alfit.domain.valueobject.ProductId;
import kg.alfit.domain.valueobject.RestaurantId;
import kg.alfit.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import kg.alfit.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException;
import kg.alfit.order.service.domain.entity.Product;
import kg.alfit.order.service.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("Restaurant could not be found!"));
        List<Product> restaurantProducts = restaurantEntities.stream()
                .map(entity -> new Product(new ProductId(entity.getProductId()), entity.getProductName(),
                        new Money(entity.getProductPrice()))).toList();

        return Restaurant.Builder.builder()
                .id(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }
}
