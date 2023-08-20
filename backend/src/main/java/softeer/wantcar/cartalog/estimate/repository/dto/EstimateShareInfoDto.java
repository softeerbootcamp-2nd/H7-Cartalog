package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstimateShareInfoDto {
    private Long trimId;
    private Long detailTrimId;
    private String exteriorColorCode;
    private String interiorColorCode;
}
