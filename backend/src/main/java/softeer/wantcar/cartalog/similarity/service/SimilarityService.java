package softeer.wantcar.cartalog.similarity.service;

import softeer.wantcar.cartalog.similarity.dto.SimilarEstimateWithSideImageResponseDto;
import softeer.wantcar.cartalog.similarity.dto.SimilarEstimateCountResponseDto;
import softeer.wantcar.cartalog.similarity.dto.SimilarEstimateResponseDto;

public interface SimilarityService {
    SimilarEstimateResponseDto getSimilarEstimateInfo(Long estimateId, Long similarEstimateId);

    SimilarEstimateWithSideImageResponseDto getSimilarEstimateInfo2(Long estimateId, Long similarEstimateId);

    SimilarEstimateCountResponseDto getSimilarEstimateCounts(Long estimateId);
}
