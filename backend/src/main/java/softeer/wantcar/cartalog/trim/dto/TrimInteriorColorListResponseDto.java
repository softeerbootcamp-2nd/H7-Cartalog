package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
public class TrimInteriorColorListResponseDto {

    private List<TrimInteriorColorDto> trimInteriorColorDtoList;

    @Getter
    @Builder
    public static class TrimInteriorColorDto {

        private String id;
        private String name;
        private String colorImageUrl;
        private String carImageUrl;
        private int price;
        private int chosen;

        public static TrimInteriorColorDto from(ModelInteriorColor color, int chosen, String carImageUrl) {
            return TrimInteriorColorDto.builder()
                    .id(color.getId())
                    .name(color.getName())
                    .colorImageUrl(color.getImageUrl())
                    .carImageUrl(carImageUrl)
                    .price(color.getPrice())
                    .chosen(chosen)
                    .build();
        }
    }

    public static TrimInteriorColorListResponseDto from(Map<ModelInteriorColor, Integer> interiorColorInfos, String imageServerPath) {
        List<TrimInteriorColorDto> interiorColorDtoList = interiorColorInfos.entrySet().stream()
                .map(entry -> TrimInteriorColorDto.from(
                        entry.getKey(),
                        entry.getValue(),
                        imageServerPath + "/image.jpg"))
                .collect(Collectors.toList());

        return TrimInteriorColorListResponseDto.builder()
                .trimInteriorColorDtoList(interiorColorDtoList)
                .build();
    }
}
