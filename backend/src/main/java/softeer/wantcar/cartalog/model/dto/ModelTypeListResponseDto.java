package softeer.wantcar.cartalog.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class ModelTypeListResponseDto {
    @Singular
    private List<ModelTypeOptionDto> modelTypes;
}
