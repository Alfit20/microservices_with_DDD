package kg.alfit.order.service.domain.ports.output.repository;

import kg.alfit.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import kg.alfit.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import kg.alfit.outbox.OutboxStatus;
import kg.alfit.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApprovalOutboxRepository {

    OrderApprovalOutboxMessage save(OrderApprovalOutboxMessage orderPaymentOutboxMessage);

    Optional<List<OrderApprovalOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(
            String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus
    );

    Optional<OrderPaymentOutboxMessage> findTypeAndSagaIdAndSagaStatus(String type,
                                                                       UUID sagaId,
                                                                       SagaStatus sagaStatus);

    void deleteByTypeAndOutboxStatusAndSagaStatus(String type,
                                                  OutboxStatus outboxStatus,
                                                  SagaStatus... sagaStatus);
}
