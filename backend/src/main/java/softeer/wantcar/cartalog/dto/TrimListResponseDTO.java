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
        private String defaultExteriorColorId;
        private String defaultInteriorColorId;
        private List<HMGDataDto> hmgData;
    }
}
