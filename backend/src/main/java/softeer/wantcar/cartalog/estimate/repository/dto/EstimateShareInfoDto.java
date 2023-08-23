package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstimateShareInfoDto {
    private Long trimId;
    private Long detailTrimId;
    private float displacement;
    private float fuelEfficiency;
    private String exteriorColorCode;
    private String exteriorColorName;
    private int exteriorColorPrice;
    private String exteriorColorImageUrl;
    private String interiorColorCode;
    private String interiorColorName;
    private int interiorColorPrice;
    private String interiorColorImageUrl;
    private String exteriorCarSideImageUrl;
    private String interiorCarImageUrl;
}
