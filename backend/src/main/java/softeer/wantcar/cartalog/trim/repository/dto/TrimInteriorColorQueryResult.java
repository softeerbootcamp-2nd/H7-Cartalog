package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;

@Getter
@Builder
@AllArgsConstructor
public class TrimInteriorColorQueryResult {
    private String code;
    private String name;
    private String imageUrl;
    private int price;
    private String interiorImageUrl;

    public TrimInteriorColorListResponseDto.TrimInteriorColorDto toTrimInteriorColorDto(int chosen) {
        return TrimInteriorColorListResponseDto.TrimInteriorColorDto.builder()
                .code(code)
                .name(name)
                .colorImageUrl(imageUrl)
                .price(price)
                .chosen(chosen)
                .carImageUrl(interiorImageUrl)
                .build();
    }
}