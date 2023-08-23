package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.Getter;

@Getter
public class EstimateInfoWithSideImageDto extends EstimateInfoDto {
    private String exteriorCarSideImageUrl;

    public EstimateInfoWithSideImageDto(String exteriorCarSideImageUrl, Long estimateId, Long detailTrimId, String trimName, int trimPrice, String modelTypeName, int modelTypePrice, String exteriorColorCode, String interiorColorCode) {
        super(estimateId, detailTrimId, trimName, trimPrice, modelTypeName, modelTypePrice, exteriorColorCode, interiorColorCode);
        this.exteriorCarSideImageUrl = exteriorCarSideImageUrl;
    }
}
