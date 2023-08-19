package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EstimateOptionInfoDto {
    private Long estimateId;
    private String optionId;
    private String name;
    private String imageUrl;
    private int price;
}
