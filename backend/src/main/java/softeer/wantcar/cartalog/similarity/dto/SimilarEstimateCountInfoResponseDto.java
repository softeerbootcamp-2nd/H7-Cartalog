package softeer.wantcar.cartalog.similarity.dto;

import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.estimate.dto.EstimateCountInfoDto;

import java.util.List;
@Getter
@Builder
public class SimilarEstimateCountInfoResponseDto {
    private Long myEstimateCount;
    private List<EstimateCountInfoDto> similarEstimateCounts;
}
