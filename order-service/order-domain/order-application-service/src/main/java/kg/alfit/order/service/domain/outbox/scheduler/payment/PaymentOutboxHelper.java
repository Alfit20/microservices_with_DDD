package kg.alfit.order.service.domain.outbox.scheduler.payment;

import kg.alfit.order.service.domain.exception.OrderDomainException;
import kg.alfit.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import kg.alfit.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import kg.alfit.outbox.OutboxStatus;
import kg.alfit.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kg.alfit.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOutboxHelper {
    private final PaymentOutboxRepository paymentOutboxRepository;


    @Transactional(readOnly = true)
    public Optional<List<OrderPaymentOutboxMessage>> getPaymentOutboxMessageByOutboxAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus
    ) {
        return paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(
                ORDER_SAGA_NAME,
                outboxStatus,
                sagaStatus);
    }

    @Transactional(readOnly = true)
    public Optional<OrderPaymentOutboxMessage> getPaymentOutboxMessageBySagaIdAndSagaStatus(
            UUID sagaId, SagaStatus... sagaStatus
    ) {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus(ORDER_SAGA_NAME, sagaId, sagaStatus);
    }

    @Transactional
    public void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
        OrderPaymentOutboxMessage response = paymentOutboxRepository.save(orderPaymentOutboxMessage);
        if (response == null) {
            log.error("Could not save OrderPaymentOutboxMessage with outbox id: {}",
                    orderPaymentOutboxMessage.getId());
            throw new OrderDomainException("Could not save OrderPaymentOutboxMessage with outbox id: " +
                    orderPaymentOutboxMessage.getId());
        }
        log.info("OrderPaymentOutboxMessage saved with outbox id: {}",
                orderPaymentOutboxMessage.getId());
    }

    @Transactional
    public void deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus
    ) {
        paymentOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(
                ORDER_SAGA_NAME,
                outboxStatus,
                sagaStatus);
    }
}
