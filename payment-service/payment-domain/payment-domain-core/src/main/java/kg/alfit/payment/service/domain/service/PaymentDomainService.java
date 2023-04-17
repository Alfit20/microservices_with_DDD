package kg.alfit.payment.service.domain.service;

import kg.alfit.payment.service.domain.entity.CreditEntry;
import kg.alfit.payment.service.domain.entity.CreditHistory;
import kg.alfit.payment.service.domain.entity.Payment;
import kg.alfit.payment.service.domain.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages);


}
