package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class TrimInteriorColorListResponseDto {
    @Singular("trimInteriorColorDto")
    private List<TrimInteriorColorDto> interiorColors;

    @Getter
    @Builder
    public static class TrimInteriorColorDto {
        private String code;
        private String name;
        private String colorImageUrl;
        private String carImageUrl;
        private int price;
        private int chosen;
    }
}
