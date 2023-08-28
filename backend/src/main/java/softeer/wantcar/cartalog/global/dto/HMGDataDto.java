package softeer.wantcar.cartalog.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class HMGDataDto implements HMGDataDtoInterface {
    private String name;
    private String value;
    private String measure;
}
