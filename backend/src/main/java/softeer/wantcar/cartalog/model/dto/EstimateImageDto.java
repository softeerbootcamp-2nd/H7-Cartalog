package softeer.wantcar.cartalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EstimateImageDto {
    private String sideExteriorImageUrl;
    private String interiorImageUrl;
}
