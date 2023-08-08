package softeer.wantcar.cartalog.trim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TrimListResponseDto {
    private String modelName;
    private List<TrimDto> trims;

    @Getter
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class TrimDto {
        private Long id;
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

    @Getter
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class DefaultTrimInfoDto {
        private List<ModelTypeDto> modelTypes;
        private String exteriorColorId;
        private String interiorColorId;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class ModelTypeDto {
        private String type;
        private OptionDto option;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class OptionDto {
        private Long id;
        private String name;
        private int price;
    }
}
