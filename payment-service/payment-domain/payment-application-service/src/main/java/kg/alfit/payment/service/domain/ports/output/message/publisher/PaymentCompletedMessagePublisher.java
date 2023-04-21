package kg.alfit.payment.service.domain.ports.output.message.publisher;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}
