package softeer.wantcar.cartalog.estimate.service;

import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateCountResponseDto;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;

import java.util.List;

public interface SimilarityService {
    SimilarEstimateResponseDto getSimilarEstimateInfo(Long estimateId, Long similarEstimateId);

    SimilarEstimateCountResponseDto getSimilarEstimateCounts(Long estimateId);

    void updateHashTagSimilarities(Long trimId, List<String> hashTags);
}
