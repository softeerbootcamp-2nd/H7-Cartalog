package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;

@Getter
@Builder
@AllArgsConstructor
public class TrimExteriorColorQueryResult {
    private String code;
    private String name;
    private String imageUrl;
    private int price;
    private String exteriorImageDirectory;

    public TrimExteriorColorListResponseDto.TrimExteriorColorDto toTrimExteriorColorDto(int chosen) {
        return TrimExteriorColorListResponseDto.TrimExteriorColorDto.builder()
                .code(code)
                .name(name)
                .colorImageUrl(imageUrl)
                .price(price)
                .chosen(chosen)
                .carImageDirectory(exteriorImageDirectory)
                .build();
    }
}
