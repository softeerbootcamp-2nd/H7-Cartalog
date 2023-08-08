package softeer.wantcar.cartalog.trim.dto;

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
        private String id;
        private String name;
        private String color;
        private String carImageUrl;
        private int price;
        private int chosen;

        public static TrimExteriorColorDto from(ModelExteriorColor exteriorColor, int chosen, String carImageUrl) {
            return TrimExteriorColorDto.builder()
                    .id(exteriorColor.getColor().getId())
                    .name(exteriorColor.getColor().getName())
                    .price(exteriorColor.getPrice())
                    .color("#" + exteriorColor.getColor().getCode())
                    .carImageUrl(carImageUrl)
                    .chosen(chosen)
                    .build();
        }
    }

    public static TrimExteriorColorListResponseDto from(Map<ModelExteriorColor, Integer> exteriorColors, String imageServerPath) {
        List<TrimExteriorColorDto> exteriorColorDtoList = exteriorColors.entrySet().stream()
                .map(entry -> TrimExteriorColorDto.from(
                        entry.getKey(),
                        entry.getValue(),
                        imageServerPath + "/image.jpg"))
                .collect(Collectors.toList());

        return TrimExteriorColorListResponseDto.builder()
                .trimExteriorColorDtoList(exteriorColorDtoList)
                .build();
    }
}
