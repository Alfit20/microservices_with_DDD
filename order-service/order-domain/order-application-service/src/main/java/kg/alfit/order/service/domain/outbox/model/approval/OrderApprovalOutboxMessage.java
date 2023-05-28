package kg.alfit.order.service.domain.outbox.model.approval;

import kg.alfit.domain.valueobject.OrderStatus;
import kg.alfit.outbox.OutboxStatus;
import kg.alfit.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderApprovalOutboxMessage {
    private UUID id;
    private UUID sagaId;
    private ZonedDateTime createAt;
    private ZonedDateTime processedAt;
    private String payload;
    private SagaStatus sagaStatus;
    private OrderStatus orderStatus;
    private OutboxStatus outboxStatus;
    private Integer version;

    public void setProcessedAt(ZonedDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}
