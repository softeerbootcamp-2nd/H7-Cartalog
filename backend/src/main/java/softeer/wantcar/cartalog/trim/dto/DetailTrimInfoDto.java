package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class DetailTrimInfoDto {
    private Long detailTrimId;
    private double displacement;
    private double fuelEfficiency;
}
