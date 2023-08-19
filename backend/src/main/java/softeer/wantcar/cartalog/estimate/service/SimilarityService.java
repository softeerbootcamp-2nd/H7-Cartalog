package softeer.wantcar.cartalog.estimate.service;

import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateCountResponseDto;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;

public interface SimilarityService {
    SimilarEstimateResponseDto getSimilarEstimateDtoList(Long estimateId);

    SimilarEstimateCountResponseDto getSimilarEstimateCounts(Long estimateId);
}
