package softeer.wantcar.cartalog.model.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ModelTypeDto {
    private String type;
    @Singular
    private List<OptionDto> options;
}
