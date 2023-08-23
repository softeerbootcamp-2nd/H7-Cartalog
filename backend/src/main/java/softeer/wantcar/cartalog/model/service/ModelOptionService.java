package softeer.wantcar.cartalog.model.service;

import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

public interface ModelOptionService {
    ModelTypeListResponseDto findModelTypeListByTrimId(Long trimId);
}
