package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.repository.dto.PendingHashTagMap;
import softeer.wantcar.cartalog.estimate.repository.dto.SimilarityInfo;

import java.util.List;

public interface SimilarityQueryRepository {
    boolean existHashTagKey(Long trimId, String hashTagKey);

    List<PendingHashTagMap> findPendingHashTagKeys(Long trimId, String hashTagKey);

    List<PendingHashTagMap> findAllHashTagKeys(Long trimId);

    List<SimilarityInfo> findSimilarities(Long trimId, String hashTagKey);

    List<Long> findSimilarEstimateIds(List<Long> hashTagIndexes);
}
