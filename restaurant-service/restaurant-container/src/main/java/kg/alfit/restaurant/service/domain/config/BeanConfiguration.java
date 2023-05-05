package kg.alfit.restaurant.service.domain.config;

import kg.alfit.restaurant.service.domain.service.RestaurantDomainService;
import kg.alfit.restaurant.service.domain.service.impl.RestaurantDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestaurantDomainService restaurantDomainService() {
        return new RestaurantDomainServiceImpl();
    }


}
