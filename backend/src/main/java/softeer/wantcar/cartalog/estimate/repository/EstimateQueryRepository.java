package softeer.wantcar.cartalog.estimate.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionListDto;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;

public interface EstimateQueryRepository {
    @Getter
    @AllArgsConstructor
    @Builder
    class EstimateOptionIdsResult {
        private Long trimId;
        private Long optionId;
        private Long packageId;
    }

    EstimateOptionListDto findEstimateOptionIdsByEstimateId(Long estimateId);

    Long findAveragePrice(Long trimId);

    Long findEstimateIdByEstimateDto(EstimateDto estimateDto);
}
