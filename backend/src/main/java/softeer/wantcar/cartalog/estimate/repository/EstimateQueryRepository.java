package softeer.wantcar.cartalog.estimate.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.estimate.repository.dto.*;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;

import java.util.List;

public interface EstimateQueryRepository {
    @Getter
    @AllArgsConstructor
    @Builder
    class EstimateOptionIdsResult {
        private Long trimId;
        private Long optionId;
        private Long packageId;
    }

    List<EstimateInfoDto> findEstimateInfoBydEstimateIds(List<Long> similarEstimateIds);

    List<EstimateOptionInfoDto> findEstimateOptionsByEstimateIds(List<Long> estimateIds);

    List<EstimateOptionInfoDto> findEstimatePackagesByEstimateIds(List<Long> estimateIds);

    EstimateOptionIdListDto findEstimateOptionIdsByEstimateId(Long estimateId);

    Long findAveragePrice(Long trimId);

    Long findEstimateIdByEstimateDto(EstimateDto estimateDto);

    List<EstimateCountDto> findEstimateCounts(List<Long> estimateIds);

    EstimateShareInfoDto findEstimateShareInfoByEstimateId(Long estimateId);

    List<Long> findEstimateModelOptionIdsByEstimateId(Long estimateId);
}
