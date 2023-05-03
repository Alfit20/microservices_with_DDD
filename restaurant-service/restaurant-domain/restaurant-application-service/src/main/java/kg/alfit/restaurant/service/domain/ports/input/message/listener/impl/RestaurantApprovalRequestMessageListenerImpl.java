package kg.alfit.restaurant.service.domain.ports.input.message.listener.impl;

import kg.alfit.restaurant.service.domain.dto.RestaurantApprovalRequest;
import kg.alfit.restaurant.service.domain.event.OrderApprovalEvent;
import kg.alfit.restaurant.service.domain.helper.RestaurantApprovalRequestHelper;
import kg.alfit.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {
    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        OrderApprovalEvent orderApprovalEvent =
                restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        orderApprovalEvent.fire();

    }
}
