package softeer.wantcar.cartalog.global.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class HMGDataDto implements HMGDataDtoInterface {
    private String name;
    private String value;
    private String measure;
}
