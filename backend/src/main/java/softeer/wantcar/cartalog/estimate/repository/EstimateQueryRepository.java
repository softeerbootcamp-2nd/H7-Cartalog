package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.dao.EstimateDao;

public interface EstimateQueryRepository {
    Long findEstimateIdByRequestDto(EstimateDao estimateDao);
}
