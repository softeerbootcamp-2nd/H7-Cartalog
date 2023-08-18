package softeer.wantcar.cartalog.trim.repository;

import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;

public interface TrimColorQueryRepository {
    TrimExteriorColorListResponseDto findTrimExteriorColorByTrimId(Long trimId);

    TrimInteriorColorListResponseDto findTrimInteriorColorByTrimIdAndExteriorColorCode(Long trimId, String colorCode);

    Long findTrimExteriorColorIdByTrimIdAndColorCode(Long trimId, String colorCode);

    Long findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(Long trimId, String exteriorColorCode, String interiorColorCode);
}
