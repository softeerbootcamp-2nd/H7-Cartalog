package softeer.wantcar.cartalog.estimate.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstimateDto {
    private Long detailTrimId;
    private Long trimExteriorColorId;
    private Long trimInteriorColorId;
    private List<Long> modelOptionIds;
    private List<Long> modelPackageIds;
}
