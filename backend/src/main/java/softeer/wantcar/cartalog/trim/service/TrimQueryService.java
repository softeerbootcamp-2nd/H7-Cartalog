package softeer.wantcar.cartalog.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.trim.dto.DetailTrimInfoDto;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrimQueryService {
    private final ModelOptionQueryRepository modelOptionQueryRepository;
    private final TrimQueryRepository trimQueryRepository;

    public DetailTrimInfoDto getDetailTrimInfo(Long trimId, List<Long> modelTypeIds) {
        List<String> categories = modelOptionQueryRepository.findModelTypeCategoriesByIds(modelTypeIds);
        if(categories == null) {
            return null;
        }
        checkCategoryOverlaped(categories);

        return trimQueryRepository.findDetailTrimInfoByTrimIdAndModelTypeIds(trimId, modelTypeIds);
    }

    private static void checkCategoryOverlaped(List<String> categories) {
        long overlapNumber = categories.stream()
                .map(c -> Collections.frequency(categories, c))
                .filter(r -> r > 1)
                .count();
        if(overlapNumber > 1) {
            throw new IllegalArgumentException();
        }
    }
}
