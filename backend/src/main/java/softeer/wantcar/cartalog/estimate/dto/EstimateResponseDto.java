package softeer.wantcar.cartalog.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstimateResponseDto {
    private Long trimId;
    private Long detailTrimId;
    private float displacement;
    private float fuel_efficiency;
    private ExteriorColorDto exteriorColor;
    private InteriorColorDto interiorColor;
    private String exteriorCarImageUrl;
    private String interiorCarImageUrl;
    private List<OptionPackageDto> modelOptions;
    private List<OptionPackageDto> selectOptionOrPackages;

    @Getter
    @Builder
    public static class ExteriorColorDto {
        private String colorCode;
        private String name;
        private int price;
        private String imageUrl;
    }

    @Getter
    @Builder
    public static class InteriorColorDto {
        private String colorCode;
        private String name;
        private int price;
        private String imageUrl;
    }

    public static class OptionPackageDto {
        private String id;
        private String name;
        private String childCategory;
        private int price;
        private String imageUrl;
    }
}
