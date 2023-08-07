package softeer.wantcar.cartalog.service;

import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;

import java.util.Map;

public interface TrimColorService {

    Map<ModelExteriorColor, Integer> findTrimExteriorColorListByTrimId(Long trimId);

    Map<ModelInteriorColor, Integer> findTrimInteriorColorListByTrimId(Long trimId, Long exteriorColorId);

}
