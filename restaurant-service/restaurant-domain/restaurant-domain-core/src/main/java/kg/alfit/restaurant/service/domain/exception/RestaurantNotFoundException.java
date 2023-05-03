package kg.alfit.restaurant.service.domain.exception;

import kg.alfit.domain.exception.DomainException;

public class RestaurantNotFoundException extends DomainException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
