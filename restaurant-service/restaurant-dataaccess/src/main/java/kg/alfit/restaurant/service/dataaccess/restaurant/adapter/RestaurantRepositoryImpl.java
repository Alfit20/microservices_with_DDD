package kg.alfit.restaurant.service.dataaccess.restaurant.adapter;

import kg.alfit.dataaccess.restaurant.entity.RestaurantEntity;
import kg.alfit.dataaccess.restaurant.repository.RestaurantJpaRepository;
import kg.alfit.restaurant.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import kg.alfit.restaurant.service.domain.entity.Restaurant;
import kg.alfit.restaurant.service.domain.ports.output.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts =
                restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);
        Optional<List<RestaurantEntity>> restaurantEntities =
                restaurantJpaRepository.findByRestaurantIdAndProductIdIn(
                        restaurant.getId().getValue(),
                        restaurantProducts
                );
        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
