package softeer.wantcar.cartalog.estimate.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class SimilarEstimateResponseDto {
    private List<SimilarEstimateDto> similarEstimates;
}
