package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;

@Getter
@AllArgsConstructor
public class TrimExteriorColorQueryResult {
    private String code;
    private String name;
    private String imageUrl;
    private int price;
    private String exteriorImageDirectory;

    public TrimExteriorColorListResponseDto.TrimExteriorColorDto toTrimExteriorColorDto() {
        return TrimExteriorColorListResponseDto.TrimExteriorColorDto.builder()
                .code(code)
                .name(name)
                .colorImageUrl(imageUrl)
                .price(price)
                .carImageDirectory(exteriorImageDirectory)
                .build();
    }
}
