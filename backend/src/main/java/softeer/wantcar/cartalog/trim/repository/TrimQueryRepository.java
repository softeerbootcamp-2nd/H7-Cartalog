package softeer.wantcar.cartalog.trim.repository;

import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;

public interface TrimQueryRepository {
    TrimListResponseDto findTrimsByBasicModelId(Long basicModelId);
}
