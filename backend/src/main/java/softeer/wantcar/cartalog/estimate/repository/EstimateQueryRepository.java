package softeer.wantcar.cartalog.estimate.repository;

public interface EstimateQueryRepository {
    long findAveragePrice(Long trimId);
}
