package softeer.wantcar.cartalog.similarity.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@JsonPropertyOrder({"trimName", "price", "modelTypes", "exteriorColorCode", "interiorColorCode", "exteriorCarSideImageUrl", "nonExistentOptions"})
public class SimilarEstimateWithSideImageResponseDto extends SimilarEstimateResponseDto {
    private String exteriorCarSideImageUrl;
}
