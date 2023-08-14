package softeer.wantcar.cartalog.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.trim.dto.DetailTrimInfoDto;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrimQueryService {
    private final ModelOptionQueryRepository modelOptionQueryRepository;
    private final TrimQueryRepository trimQueryRepository;

    public DetailTrimInfoDto getDetailTrimInfo(Long trimId, List<Long> modelTypeIds) {
        List<String> categories = modelOptionQueryRepository.findModelTypeCategoriesByIds(modelTypeIds);
        if (categories == null) {
            return null;
        }
        checkCategoryOverlapped(categories, modelTypeIds);

        return trimQueryRepository.findDetailTrimInfoByTrimIdAndModelTypeIds(trimId, modelTypeIds);
    }

    private static void checkCategoryOverlapped(List<String> categories, List<Long> modelTypeIds) {
        if (categories.size() != modelTypeIds.size()) {
            throw new IllegalArgumentException();
        }
    }
}
