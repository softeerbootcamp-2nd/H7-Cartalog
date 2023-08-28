package softeer.wantcar.cartalog.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PowerTrainHMGDataDto implements HMGDataDtoInterface {
    private String name;
    private float value;
    private String rpm;
    private String measure;
}
