package softeer.wantcar.cartalog.trim.service;

import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;

public interface TrimColorService {
    TrimExteriorColorListResponseDto findTrimExteriorColor(Long trimId);

    TrimInteriorColorListResponseDto findTrimInteriorColor(Long trimId, String exteriorColorCode);
}
