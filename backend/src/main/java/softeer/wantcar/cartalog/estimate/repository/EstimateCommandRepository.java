package softeer.wantcar.cartalog.estimate.repository;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public interface EstimateCommandRepository {
    @Getter
    @Builder
    class EstimateSaveDto {
        private Long detailTrimId;
        private Long trimExteriorColorId;
        private Long trimInteriorColorId;
        private List<Long> modelOptionIds;
        private List<Long> modelPackageIds;
    }

    void save(EstimateSaveDto estimateSaveDto);
}
