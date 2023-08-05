package softeer.wantcar.cartalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class TrimListResponseDTO {
    private String modelName;
    private List<TrimDto> trims;

    @AllArgsConstructor
    @Builder
    public static class TrimDto {
        private String name;
        private String description;
        private int minPrice;
        private int maxPrice;
        private DefaultExteriorColorDto defaultExteriorColor;
        private DefaultInteriorColorDto defaultInteriorColor;
        private List<HMGDataDto> hmgDataDtos;
    }

    @AllArgsConstructor
    @Builder
    public static class DefaultExteriorColorDto {
        private String id;
        private String name;
    }

    @AllArgsConstructor
    @Builder
    public static class DefaultInteriorColorDto {
        private String id;
        private String name;
    }
}
