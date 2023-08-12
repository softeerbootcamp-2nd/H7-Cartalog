package softeer.wantcar.cartalog.trim.repository;

import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;

public interface TrimColorQueryRepository {
    TrimExteriorColorListResponseDto findTrimExteriorColorByTrimId(Long trimId);
}
