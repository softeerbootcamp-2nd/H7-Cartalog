package softeer.wantcar.cartalog.similarity.dto;

import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionInfoDto;

import java.util.List;

@Builder
@Getter
public class SimilarEstimateResponseDto {
    private String trimName;
    private int price;
    private List<String> modelTypes;
    private String exteriorColorCode;
    private String interiorColorCode;
    private List<EstimateOptionInfoDto> nonExistentOptions;
}
