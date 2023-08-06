package softeer.wantcar.cartalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TrimListResponseDTO {
    private String modelName;
    private List<TrimDto> trims;

    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class TrimDto {
        private String name;
        private String description;
        private int minPrice;
        private int maxPrice;
        private String exteriorImage;
        private String interiorImage;
        private String wheelImage;
        private List<HMGDataDto> hmgData;
        private DefaultTrimInfoDto defaultInfo;
    }

    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class DefaultTrimInfoDto {
        private List<ModelTypeDto> modelTypes;
        private String exteriorColorId;
        private String interiorColorId;
    }

    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class ModelTypeDto {
        private String type;
        private OptionDto option;
    }

    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class OptionDto {
        private Long id;
        private String name;
        private int price;
    }
}
