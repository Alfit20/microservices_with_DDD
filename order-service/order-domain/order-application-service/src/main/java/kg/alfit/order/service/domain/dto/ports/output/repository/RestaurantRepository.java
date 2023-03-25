package kg.alfit.order.service.domain.dto.ports.output.repository;

import kg.alfit.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
