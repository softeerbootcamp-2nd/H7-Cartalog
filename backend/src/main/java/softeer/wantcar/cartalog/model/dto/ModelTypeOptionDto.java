package softeer.wantcar.cartalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ModelTypeOptionDto implements OptionDto {
    private Long id;
    private String name;
    private int price;
    private int chosen;
    private String imageUrl;
    private String description;
    private List<HMGDataDto> hmgData;
}
