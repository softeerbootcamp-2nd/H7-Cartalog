package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;

public interface EstimateQueryRepository {
    Long findEstimateIdByRequestDto(EstimateDto estimateDto);
}
