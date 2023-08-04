package softeer.wantcar.cartalog.service;

import java.util.List;

public interface ColorService {

    List<TrimExteriorColor> findTrimExteriorColorListByTrimId(Long trimId);

    List<TrimInteriorColor> findTrimInteriorColorListByTrimId(Long trimId, Long exteriorColorId);

}
