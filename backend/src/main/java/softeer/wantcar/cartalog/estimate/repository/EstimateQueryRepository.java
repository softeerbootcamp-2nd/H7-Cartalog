package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;

public interface EstimateQueryRepository {
    Long findEstimateIdByRequestDto(EstimateRequestDto estimateRequestDto);
}
