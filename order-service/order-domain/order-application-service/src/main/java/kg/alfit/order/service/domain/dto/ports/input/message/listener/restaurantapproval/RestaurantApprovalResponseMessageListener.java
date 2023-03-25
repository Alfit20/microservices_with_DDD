package kg.alfit.order.service.domain.dto.ports.input.message.listener.restaurantapproval;

import kg.alfit.order.service.domain.dto.dto.response.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
