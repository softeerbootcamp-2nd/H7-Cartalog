package softeer.wantcar.cartalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class ModelTypeListResponseDto {
    private List<ModelTypeDto> modelTypes;

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class ModelTypeDto {
        private String type;
        private List<OptionDto> options;
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class OptionDto {
        private String name;
        private int price;
        private int chosen;
        private String image;
        private String description;
        private List<HMGDataDto> hmgData;
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class HMGDataDto {
        private String name;
        private String value;
    }
}
