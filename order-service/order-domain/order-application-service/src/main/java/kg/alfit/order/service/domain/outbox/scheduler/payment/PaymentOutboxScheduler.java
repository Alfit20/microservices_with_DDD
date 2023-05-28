package kg.alfit.order.service.domain.outbox.scheduler.payment;

import kg.alfit.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import kg.alfit.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import kg.alfit.outbox.OutboxScheduler;
import kg.alfit.outbox.OutboxStatus;
import kg.alfit.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOutboxScheduler implements OutboxScheduler {
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final PaymentRequestMessagePublisher paymentRequestMessagePublisher;


    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    @Transactional
    @Override
    public void processOutboxMessage() {
        Optional<List<OrderPaymentOutboxMessage>> outboxMessageResponse =
                paymentOutboxHelper.getPaymentOutboxMessageByOutboxAndSagaStatus(
                        OutboxStatus.STARTED,
                        SagaStatus.STARTED,
                        SagaStatus.COMPENSATING
                );
        if (outboxMessageResponse.isPresent() && outboxMessageResponse.get().size() > 0) {
            List<OrderPaymentOutboxMessage> outboxMessages = outboxMessageResponse.get();
            log.info("Received {} OrderPaymentOutboxMessage with ids: {}, sending to message bus",
                    outboxMessages.size(),
                    outboxMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(outboxMessage -> paymentRequestMessagePublisher.publish(
                    outboxMessage, this::updateOutboxStatus
                    ));
            log.info("{} OrderPaymentOutboxMessage sent to message bus!", outboxMessages.size());
        }
    }

    private void updateOutboxStatus(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                                    OutboxStatus outboxStatus) {
        orderPaymentOutboxMessage.setOutboxStatus(outboxStatus);
        paymentOutboxHelper.save(orderPaymentOutboxMessage);
        log.info("OrderPaymentOutboxMessage is updated with outbox status: {}",
                outboxStatus.name());
    }
}
