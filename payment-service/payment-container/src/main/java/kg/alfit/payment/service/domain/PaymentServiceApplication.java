package kg.alfit.payment.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "kg.alfit.payment.service.dataaccess")
@EntityScan(basePackages = "kg.alfit.payment.service.dataaccess")
@SpringBootApplication(scanBasePackages = "kg.alfit")
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
