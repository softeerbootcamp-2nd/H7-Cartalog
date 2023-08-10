package softeer.wantcar.cartalog.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PowerTrainHMGDataDto implements HMGDataDtoInterface {
    private String name;
    private float value;
    private String rpm;
    private String measure;
}
