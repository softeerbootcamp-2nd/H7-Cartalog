package softeer.wantcar.cartalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@EqualsAndHashCode
public class TrimExteriorColorListResponseDto {

    List<TrimExteriorColorDto> trimExteriorColorDtoList;

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class TrimExteriorColorDto {

        private Long id;
        private String name;
        private String code;
        private int price;
        private int chosen;

        public static TrimExteriorColorDto from(TrimExteriorColor color) {
            return TrimExteriorColorDto.builder()
                    .id(color.getId())
                    .name(color.getName())
                    .price(color.getPrice())
                    .code(color.getCode())
                    .chosen(color.getChosen())
                    .build();
        }

    }

    public static TrimExteriorColorListResponseDto from(List<TrimExteriorColor> colors) {
        List<TrimExteriorColorDto> trimExteriorColorDtoList = colors.stream()
                .map(TrimExteriorColorDto::from)
                .collect(Collectors.toUnmodifiableList());

        return TrimExteriorColorListResponseDto.builder()
                .trimExteriorColorDtoList(trimExteriorColorDtoList)
                .build();

    }

}
