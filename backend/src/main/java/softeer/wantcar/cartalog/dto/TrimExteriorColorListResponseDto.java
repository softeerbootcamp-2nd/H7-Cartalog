package softeer.wantcar.cartalog.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;

import java.util.List;
import java.util.Map;
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

        public static TrimExteriorColorDto from(ModelExteriorColor exteriorColor, int chosen) {
            return TrimExteriorColorDto.builder()
                    .id(exteriorColor.getId())
                    .name(exteriorColor.getColor().getName())
                    .price(exteriorColor.getPrice())
                    .code("#" + exteriorColor.getColor().getCode())
                    .chosen(chosen)
                    .build();
        }
    }

    public static TrimExteriorColorListResponseDto from(Map<ModelExteriorColor, Integer> exteriorColors) {
        List<TrimExteriorColorDto> exteriorColorDtos = exteriorColors.entrySet().stream()
                .map(entry -> TrimExteriorColorDto.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return TrimExteriorColorListResponseDto.builder()
                .trimExteriorColorDtoList(exteriorColorDtos)
                .build();
    }
}
