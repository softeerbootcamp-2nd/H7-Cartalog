package softeer.wantcar.cartalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ModelTypeDto {
    private String type;
    @Singular
    private List<OptionDto> options;
}
