package softeer.wantcar.cartalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;

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
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class ModelTypeOptionDto extends OptionDto {
        private Long id;
        private String name;
        private int price;
        private int chosen;
        private String imageUrl;
        private String description;
        private List<HMGDataDto> hmgData;
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class PowerTrainOptionDto extends OptionDto {
        private Long id;
        private String name;
        private int price;
        private int chosen;
        private String imageUrl;
        private String description;
        private List<PowerTrainHMGDataDto> powerTrainHMGData;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class PowerTrainHMGDataDto {
        private String name;
        private float value;
        private String rpm;
        private String measure;
    }

    public static abstract class OptionDto {
    }
}
