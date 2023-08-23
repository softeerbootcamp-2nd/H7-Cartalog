package softeer.wantcar.cartalog.estimate.service;

import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.dto.EstimateResponseDto;

public interface EstimateService {
    Long saveOrFindEstimateId(EstimateRequestDto estimateRequestDto);

    EstimateResponseDto findEstimateByEstimateId(Long estimateId);
}
