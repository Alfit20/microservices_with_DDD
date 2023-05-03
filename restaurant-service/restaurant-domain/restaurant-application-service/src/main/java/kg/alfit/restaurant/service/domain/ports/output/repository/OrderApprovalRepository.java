package kg.alfit.restaurant.service.domain.ports.output.repository;

import kg.alfit.restaurant.service.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
