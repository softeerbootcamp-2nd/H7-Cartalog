package softeer.wantcar.cartalog.estimate.service;

import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateCountResponseDto;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;

public interface SimilarityService {
    SimilarEstimateResponseDto getSimilarEstimateInfo(Long estimateId, Long similarEstimateId);

    SimilarEstimateCountResponseDto getSimilarEstimateCounts(Long estimateId);

    void updateHashTagSimilarities(Long trimId, String hashTagKey);
}
