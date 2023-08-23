package softeer.wantcar.cartalog.estimate.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstimateCountInfoDto {
    private Long estimateId;
    private Long count;
    private EstimateResponseDto estimateInfo;
}
