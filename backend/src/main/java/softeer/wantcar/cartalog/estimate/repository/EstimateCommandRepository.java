package softeer.wantcar.cartalog.estimate.repository;

import softeer.wantcar.cartalog.estimate.dao.EstimateDao;

public interface EstimateCommandRepository {
    void save(EstimateDao estimateDao);
}
