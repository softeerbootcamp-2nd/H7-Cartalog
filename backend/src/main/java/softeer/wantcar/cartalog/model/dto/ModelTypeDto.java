package softeer.wantcar.cartalog.model.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ModelTypeDto {
    private String type;
    @Singular
    private List<OptionDto> options;
}
