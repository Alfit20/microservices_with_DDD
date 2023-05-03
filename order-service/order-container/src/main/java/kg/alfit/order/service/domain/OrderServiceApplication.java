package kg.alfit.order.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"kg.alfit.order.service.dataaccess", "kg.alfit.dataaccess"})
@EntityScan(basePackages = {"kg.alfit.order.service.dataaccess", "kg.alfit.dataacess"})
@SpringBootApplication(scanBasePackages = "kg.alfit")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
