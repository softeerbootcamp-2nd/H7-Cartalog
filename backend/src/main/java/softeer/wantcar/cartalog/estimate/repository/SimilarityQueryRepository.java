package softeer.wantcar.cartalog.estimate.repository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.estimate.repository.dto.HashTagMap;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateInfoDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionInfoDto;

import java.util.List;

public interface SimilarityQueryRepository {
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    class SimilarityInfo {
        private HashTagMap hashTagMap;
        private Float similarity;
    }

    List<HashTagMap> findPendingHashTagMapByTrimIdAndHashTagKey(Long trimId, String hashTagKey);

    List<String> findSimilarHashTagKeysByTrimIdAndHashTagKey(Long trimId, String hashTagKey);

    List<Long> findSimilarEstimateIdsByTrimIdAndHashTagKey(Long trimId, List<String> hashTagKey);

    List<EstimateInfoDto> findSimilarEstimateInfoBydEstimateIds(List<Long> similarEstimateIds);

    List<EstimateOptionInfoDto> findSimilarEstimateOptionsByEstimateIds(List<Long> similarEstimateIds);

    List<EstimateOptionInfoDto> findSimilarEstimatePackagesBtEstimateIds(List<Long> similarEstimateIds);

}
