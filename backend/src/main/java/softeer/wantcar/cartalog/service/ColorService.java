package softeer.wantcar.cartalog.service;

import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;

import java.util.List;

public interface ColorService {

    List<TrimExteriorColor> findTrimExteriorColorListByTrimId(Long trimId);

}
