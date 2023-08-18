package softeer.wantcar.cartalog.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstimateSaveDto {
    private Long detailTrimId;
    private Long trimExteriorColorId;
    private Long trimInteriorColorId;
    private List<Long> modelOptionIds;
    private List<Long> modelPackageIds;
}
