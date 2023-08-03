package softeer.wantcar.cartalog.service;

import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;
import softeer.wantcar.cartalog.entity.color.TrimInteriorColor;

import java.util.List;

public interface ColorService {

    List<TrimExteriorColor> findTrimExteriorColorListByTrimId(Long trimId);

    List<TrimInteriorColor> findTrimInteriorColorListByTrimId(Long trimId, Long exteriorColorId);

}
