package softeer.wantcar.cartalog.model.repository;

import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

import java.util.List;

public interface ModelOptionQueryRepository {

    ModelTypeListResponseDto findByModelTypeOptionsByBasicModelId(Long id);

    List<String> findModelTypeCategoriesByIds(List<Long> modelTypeIds);
}
