package softeer.wantcar.cartalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ModelPerformanceDto {
    private float displacement;
    private float fuelEfficiency;
}
