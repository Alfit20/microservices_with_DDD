package kg.alfit.payment.service.domain.service;

import kg.alfit.domain.event.publisher.DomainEventPublisher;
import kg.alfit.payment.service.domain.entity.CreditEntry;
import kg.alfit.payment.service.domain.entity.CreditHistory;
import kg.alfit.payment.service.domain.entity.Payment;
import kg.alfit.payment.service.domain.event.PaymentCancelledEvent;
import kg.alfit.payment.service.domain.event.PaymentCompletedEvent;
import kg.alfit.payment.service.domain.event.PaymentEvent;
import kg.alfit.payment.service.domain.event.PaymentFailedEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages,
                                            DomainEventPublisher<PaymentCompletedEvent>
                                                    paymentCompletedEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages, DomainEventPublisher<PaymentCancelledEvent> paymentCompletedEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);


}
