package kg.alfit.restaurant.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"kg.alfit.restaurant.service.dataaccess", "kg.alfit.dataaccess"})
@EntityScan(basePackages = {"kg.alfit.restaurant.service.dataaccess", "kg.alfit.dataaccess"})
@SpringBootApplication(scanBasePackages = "kg.alfit")
public class RestaurantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
}
