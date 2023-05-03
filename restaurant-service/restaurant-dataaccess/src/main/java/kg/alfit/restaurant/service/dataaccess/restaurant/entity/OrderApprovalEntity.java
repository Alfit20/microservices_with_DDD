package kg.alfit.restaurant.service.dataaccess.restaurant.entity;

import kg.alfit.domain.valueobject.OrderApprovalStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_approval", schema = "restaurant")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderApprovalEntity {

    @Id
    private UUID id;
    private UUID restaurantId;
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private OrderApprovalStatus orderApprovalStatus;
}
