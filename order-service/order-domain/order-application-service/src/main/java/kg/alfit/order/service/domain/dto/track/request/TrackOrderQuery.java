package kg.alfit.order.service.domain.dto.track.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class TrackOrderQuery {

    @NotNull
    private final UUID orderTrackingId;
}
