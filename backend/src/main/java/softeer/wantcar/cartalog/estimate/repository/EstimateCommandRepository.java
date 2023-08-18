package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.dto.EstimateSaveDto;

public interface EstimateCommandRepository {
    void save(EstimateSaveDto estimateSaveDto);
}
