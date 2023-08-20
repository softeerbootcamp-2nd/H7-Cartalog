package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.repository.dto.PendingHashTagMap;

import java.util.List;

public interface SimilarityQueryRepository {
    List<PendingHashTagMap> findPendingHashTagMapByTrimIdAndHashTagKey(Long trimId, String hashTagKey);

    List<String> findSimilarHashTagKeysByTrimIdAndHashTagKey(Long trimId, String hashTagKey);

    List<Long> findSimilarEstimateIdsByTrimIdAndHashTagKey(Long trimId, List<String> hashTagKey);

    List<String> findAllCalculatedHashTagKeys();
}
