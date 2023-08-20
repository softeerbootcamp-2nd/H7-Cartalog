package softeer.wantcar.cartalog.estimate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class EstimateResponseDto {
    private Long trimId;
    private Long detailTrimId;
    private float displacement;
    private float fuelEfficiency;
    private ColorDto exteriorColor;
    private ColorDto interiorColor;
    private String exteriorCarDirectory;
    private String interiorCarImageUrl;
    @Singular
    private List<OptionPackageDto> modelTypes;
    @Singular
    private List<OptionPackageDto> selectOptionOrPackages;

    @Getter
    @Builder
    public static class ColorDto {
        private String colorCode;
        private String name;
        private int price;
        private String imageUrl;
    }

    @Getter
    @Builder
    public static class OptionPackageDto {
        private String id;
        private String name;
        private String childCategory;
        private int price;
        private String imageUrl;
    }
}
