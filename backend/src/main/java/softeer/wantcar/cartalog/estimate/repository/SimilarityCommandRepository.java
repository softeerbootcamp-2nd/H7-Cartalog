package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.repository.dto.SimilarityInfo;

import java.util.List;

public interface SimilarityCommandRepository {
    void updateLastCalculatedIndex(Long trimId, String hashTagKey, long lastIndex);

    void deleteSimilarities(Long trimId, String hashTagKey);

    void saveSimilarities(Long trimId, String hashTagKey, List<SimilarityInfo> similarityInfos);

    void saveHashTagKey(Long trimId, String hashTagKey, long lastIndex);
}
