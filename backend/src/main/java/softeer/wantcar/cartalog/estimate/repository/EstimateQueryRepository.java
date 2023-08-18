package softeer.wantcar.cartalog.estimate.repository;

public interface EstimateQueryRepository {
    Long findEstimateIdByRequestDto(EstimateCommandRepository.EstimateSaveDto estimateSaveDto);
}
