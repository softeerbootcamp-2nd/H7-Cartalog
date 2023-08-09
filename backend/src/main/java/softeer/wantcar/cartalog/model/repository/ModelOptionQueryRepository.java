package softeer.wantcar.cartalog.model.repository;

import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

public interface ModelOptionQueryRepository {

    ModelTypeListResponseDto findByModelId(Long id);

}
