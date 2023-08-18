package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.service.dto.PendingHashTagSimilaritySaveDto;

public interface SimilarityCommandRepository {
    void savePendingHashTagSimilarity(PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto);
}
