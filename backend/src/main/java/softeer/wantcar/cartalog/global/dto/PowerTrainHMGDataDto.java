package softeer.wantcar.cartalog.global.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class PowerTrainHMGDataDto implements HMGDataDtoInterface {
    private String name;
    private float value;
    private String rpm;
    private String measure;
}
