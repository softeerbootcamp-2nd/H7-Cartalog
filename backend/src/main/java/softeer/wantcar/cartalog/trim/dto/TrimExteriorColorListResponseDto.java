package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class TrimExteriorColorListResponseDto {
    @Singular("trimExteriorColorDto")
    List<TrimExteriorColorDto> exteriorColors;

    @Getter
    @Builder
    public static class TrimExteriorColorDto {
        private String code;
        private String name;
        private String colorImageUrl;
        private String carImageDirectory;
        private int price;
        private int chosen;
    }
}
