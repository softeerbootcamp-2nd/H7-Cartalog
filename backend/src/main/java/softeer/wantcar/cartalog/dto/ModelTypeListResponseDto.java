package softeer.wantcar.cartalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.entity.HMGData;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class ModelTypeListResponseDto {
    private List<ModelTypeDto> modelTypeDtos;

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class ModelTypeDto {
        private String name;
        private List<Option> options;
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class Option {
        private String name;
        private int price;
        private int chosen;
        private String image;
        private String description;
        private List<HMGData> HMGData;
    }
}
