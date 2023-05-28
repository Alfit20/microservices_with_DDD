package kg.alfit.order.service.domain.outbox.scheduler.payment;

import kg.alfit.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import kg.alfit.outbox.OutboxScheduler;
import kg.alfit.outbox.OutboxStatus;
import kg.alfit.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOutboxCleanerScheduler implements OutboxScheduler {
    private final PaymentOutboxHelper paymentOutboxHelper;

    @Scheduled(cron = "@midnight")
    @Override
    public void processOutboxMessage() {
        Optional<List<OrderPaymentOutboxMessage>> outboxMessagesResponse =
                paymentOutboxHelper.getPaymentOutboxMessageByOutboxAndSagaStatus(
                        OutboxStatus.COMPLETED,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED
                );
        if (outboxMessagesResponse.isPresent()) {
            List<OrderPaymentOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} OrderPaymentOutboxMessage for clean-up. " +
                            "The payloads: {}",
                    outboxMessages.size(),
                    outboxMessages.stream().map(OrderPaymentOutboxMessage::getPayload)
                            .collect(Collectors.joining("\n")));
            paymentOutboxHelper.deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(
                    OutboxStatus.COMPLETED,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.FAILED,
                    SagaStatus.COMPENSATED
            );
            log.info("{} OrderPaymentOutboxMessage deleted!", outboxMessages.size());
        }
    }
}
