package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;

@Getter
@AllArgsConstructor
public class TrimInteriorColorQueryResult {
    private String code;
    private String name;
    private String imageUrl;
    private int price;
    private String interiorImageUrl;

    public TrimInteriorColorListResponseDto.TrimInteriorColorDto toTrimInteriorColorDto() {
        return TrimInteriorColorListResponseDto.TrimInteriorColorDto.builder()
                .code(code)
                .name(name)
                .colorImageUrl(imageUrl)
                .price(price)
                .carImageUrl(interiorImageUrl)
                .build();
    }
}