package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EstimateInfoDto {
    private Long estimateId;
    private Long detailTrimId;
    private String trimName;
    private int trimPrice;
    private String modelTypeName;
    private int modelTypePrice;
    private String exteriorColorCode;
    private String interiorColorCode;
}
