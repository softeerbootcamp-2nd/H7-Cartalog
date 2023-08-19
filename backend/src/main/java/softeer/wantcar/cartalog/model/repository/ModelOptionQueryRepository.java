package softeer.wantcar.cartalog.model.repository;

import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

import java.util.List;

public interface ModelOptionQueryRepository {

    ModelTypeListResponseDto findByModelTypeOptionsByTrimId(Long trimId);

    List<String> findModelTypeCategoriesByIds(List<Long> modelTypeIds);

    List<String> findHashTagFromOptionsByOptionIds(List<Long> optionIds);

    List<String> findHashTagFromPackagesByPackageIds(List<Long> packageIds);
}
