package softeer.wantcar.cartalog.trim.service;

import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;

public interface TrimOptionService {
    TrimOptionListResponseDto getTrimOptionList(Long detailTrimId, String interiorColorCode);
}
