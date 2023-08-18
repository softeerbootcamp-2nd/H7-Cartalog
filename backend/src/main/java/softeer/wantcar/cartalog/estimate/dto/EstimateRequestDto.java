package softeer.wantcar.cartalog.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstimateRequestDto {
    private Long detailTrimId;
    private String exteriorColorCode;
    private String interiorColorCode;
    private List<String> selectOptionOrPackageIds;
}
