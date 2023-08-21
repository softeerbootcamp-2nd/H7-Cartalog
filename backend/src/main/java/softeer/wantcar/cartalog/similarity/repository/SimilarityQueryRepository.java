package softeer.wantcar.cartalog.similarity.repository;

import softeer.wantcar.cartalog.similarity.repository.dto.PendingHashTagMap;
import softeer.wantcar.cartalog.similarity.repository.dto.SimilarityInfo;

import java.util.List;

public interface SimilarityQueryRepository {
    boolean existHashTagKey(Long trimId, String hashTagKey);

    List<PendingHashTagMap> findPendingHashTagKeys(Long trimId, String hashTagKey);

    List<PendingHashTagMap> findAllHashTagKeys(Long trimId);

    List<SimilarityInfo> findSimilarities(Long trimId, String hashTagKey);

    List<Long> findSimilarEstimateIds(List<Long> hashTagIndexes);
}
