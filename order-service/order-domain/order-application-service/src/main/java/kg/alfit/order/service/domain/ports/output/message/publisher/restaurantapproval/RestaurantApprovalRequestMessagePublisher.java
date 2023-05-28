package kg.alfit.order.service.domain.ports.output.message.publisher.restaurantapproval;

import kg.alfit.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import kg.alfit.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface RestaurantApprovalRequestMessagePublisher {

    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
