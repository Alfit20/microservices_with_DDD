package kg.alfit.restaurant.service.domain.ports.input.message.listener;

import kg.alfit.restaurant.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
