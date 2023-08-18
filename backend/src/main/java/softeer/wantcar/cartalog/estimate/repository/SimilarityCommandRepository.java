package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.dao.PendingHashTagSimilaritySaveDao;

public interface SimilarityCommandRepository {
    void savePendingHashTagSimilarity(PendingHashTagSimilaritySaveDao pendingHashTagSimilaritySaveDao);
}
