package kg.alfit.order.service.domain.valueobject;

import kg.alfit.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {


    public TrackingId(UUID value) {
        super(value);
    }
}
