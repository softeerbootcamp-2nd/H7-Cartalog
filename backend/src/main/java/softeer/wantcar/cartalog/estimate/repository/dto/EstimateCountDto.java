package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EstimateCountDto {
    private Long estimateId;
    private Long count;
    private int price;
}
