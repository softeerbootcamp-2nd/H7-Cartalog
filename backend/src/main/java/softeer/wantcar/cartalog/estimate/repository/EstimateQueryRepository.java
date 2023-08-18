package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.dto.EstimateSaveDto;

public interface EstimateQueryRepository {
    Long findEstimateIdByRequestDto(EstimateSaveDto estimateSaveDto);
}
