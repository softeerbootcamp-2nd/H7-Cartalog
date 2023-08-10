package softeer.wantcar.cartalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.global.dto.PowerTrainHMGDataDto;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PowerTrainOptionDto implements OptionDto {
    private Long id;
    private String name;
    private int price;
    private int chosen;
    private String imageUrl;
    private String description;
    private List<PowerTrainHMGDataDto> powerTrainHMGData;
}
