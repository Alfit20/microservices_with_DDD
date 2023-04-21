package kg.alfit.payment.service.domain.exception;

import kg.alfit.domain.exception.DomainException;

public class PaymentApplicationsServiceException extends DomainException {

    public PaymentApplicationsServiceException(String message) {
        super(message);
    }

    public PaymentApplicationsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
