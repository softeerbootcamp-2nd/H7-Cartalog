package softeer.wantcar.cartalog.trim.repository;

import softeer.wantcar.cartalog.trim.repository.dto.TrimExteriorColorQueryResult;
import softeer.wantcar.cartalog.trim.repository.dto.TrimInteriorColorQueryResult;

import java.util.List;

public interface TrimColorQueryRepository {
    List<TrimExteriorColorQueryResult> findTrimExteriorColorsByTrimId(Long trimId);

    List<TrimInteriorColorQueryResult> findTrimInteriorColorsByTrimIdAndExteriorColorCode(Long trimId, String exteriorColorCode);

    Long findTrimExteriorColorIdByTrimIdAndColorCode(Long trimId, String colorCode);

    Long findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(Long trimId, String exteriorColorCode, String interiorColorCode);
}
