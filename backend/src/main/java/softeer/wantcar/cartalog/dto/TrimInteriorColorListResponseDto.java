package softeer.wantcar.cartalog.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TrimInteriorColorListResponseDto {

    private List<TrimInteriorColorDto> trimInteriorColorDtoList;

    @Getter
    @Builder
    public static class TrimInteriorColorDto {

        private Long id;
        private String name;
        private String imageUrl;
        private int price;
        private int chosen;

        public static TrimInteriorColorDto from(TrimInteriorColor color) {
            return TrimInteriorColorDto.builder()
                    .id(color.getId())
                    .name(color.getName())
                    .imageUrl(color.getImageUrl())
                    .price(color.getPrice())
                    .chosen(color.getChosen())
                    .build();
        }

    }

    public static TrimInteriorColorListResponseDto from(List<TrimInteriorColor> colors) {
        List<TrimInteriorColorDto> trimInteriorColorDtoList = colors.stream()
                .map(TrimInteriorColorDto::from)
                .collect(Collectors.toUnmodifiableList());

        return TrimInteriorColorListResponseDto.builder()
                .trimInteriorColorDtoList(trimInteriorColorDtoList)
                .build();
    }

}
