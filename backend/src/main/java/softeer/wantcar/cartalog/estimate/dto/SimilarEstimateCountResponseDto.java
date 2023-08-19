package softeer.wantcar.cartalog.estimate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateCountDto;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SimilarEstimateCountResponseDto {
    private Long myEstimateCount;
    private List<EstimateCountDto> similarEstimateCounts;
}
