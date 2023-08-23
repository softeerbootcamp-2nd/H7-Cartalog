package softeer.wantcar.cartalog.trim.repository;

import softeer.wantcar.cartalog.trim.dto.DetailTrimInfoDto;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;

import java.util.List;

public interface TrimQueryRepository {
    TrimListResponseDto findTrimsByBasicModelId(Long basicModelId);

    DetailTrimInfoDto findDetailTrimInfoByTrimIdAndModelTypeIds(Long trimId, List<Long> modelTypeIds);

    Long findTrimIdByDetailTrimId(Long detailTrimId);
}
