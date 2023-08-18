package softeer.wantcar.cartalog.estimate.service;

import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;

public interface EstimateService {
    Long saveOrFindEstimateId(EstimateRequestDto estimateRequestDto);
}
