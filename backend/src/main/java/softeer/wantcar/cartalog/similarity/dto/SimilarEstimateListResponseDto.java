package softeer.wantcar.cartalog.similarity.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SimilarEstimateListResponseDto {
    private List<SimilarEstimateWithSideImageResponseDto> similarEstimates;
}
