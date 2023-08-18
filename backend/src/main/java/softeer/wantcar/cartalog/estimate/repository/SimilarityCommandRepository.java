package softeer.wantcar.cartalog.estimate.repository;

import lombok.Builder;
import lombok.Getter;

public interface SimilarityCommandRepository {
    @Getter
    @Builder
    class PendingHashTagSimilaritySaveDto {
        private String hashTagKey;
        private String pendingHashTagLeftKey;
        private Long trimId;
    }

    void savePendingHashTagSimilarity(PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto);
}
