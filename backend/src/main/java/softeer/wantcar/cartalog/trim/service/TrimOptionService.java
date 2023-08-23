package softeer.wantcar.cartalog.trim.service;

import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;

public interface TrimOptionService {
    TrimOptionListResponseDto getTrimOptionList(Long detailTrimId, String interiorColorCode);

    TrimOptionDetailResponseDto getTrimOptionDetail(Long detailTrimOptionId);

    TrimPackageDetailResponseDto getTrimPackageDetail(Long packageId);
}
