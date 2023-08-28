package softeer.wantcar.cartalog.model.repository;

import softeer.wantcar.cartalog.model.repository.dto.ModelTypeDto;

import java.util.List;

public interface ModelOptionQueryRepository {

    List<ModelTypeDto> findModelTypeByTrimId(Long trimId);

    List<String> findModelTypeCategoriesByIds(List<Long> modelTypeIds);

    List<String> findHashTagFromOptionsByOptionIds(List<Long> optionIds);

    List<String> findHashTagFromPackagesByPackageIds(List<Long> packageIds);
}
