package softeer.wantcar.cartalog.estimate.service;

import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;

public interface SimilarityService {
    SimilarEstimateResponseDto getSimilarEstimateDtoList(Long estimateId);
}
