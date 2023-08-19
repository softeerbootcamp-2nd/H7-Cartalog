package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.service.dto.PendingHashTagSimilaritySaveDto;

import java.util.List;
import java.util.Map;

public interface SimilarityCommandRepository {
    void deletePending(Long trimId, String hashTagKey);

    void saveCalculatedHashTagKeys(Long trimId, String hashTagKey, Map<String, Double> similarities);

    void savePendingHashTagSimilarities(PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto);
}
