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
        private DefaultExteriorColorDto defaultExteriorColor;
        private DefaultInteriorColorDto defaultInteriorColor;
        private List<HMGDataDto> hmgData;
    }

    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class DefaultExteriorColorDto {
        private String id;
        private String name;
    }

    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class DefaultInteriorColorDto {
        private String id;
        private String name;
    }
}
