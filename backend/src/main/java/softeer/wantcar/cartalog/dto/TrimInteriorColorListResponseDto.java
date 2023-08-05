package softeer.wantcar.cartalog.dto;

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
        private String imageUrl;
        private int price;
        private int chosen;

        public static TrimInteriorColorDto from(ModelInteriorColor color, int chosen) {
            return TrimInteriorColorDto.builder()
                    .id(color.getId())
                    .name(color.getName())
                    .imageUrl(color.getImageUrl())
                    .price(color.getPrice())
                    .chosen(chosen)
                    .build();
        }

    }

    public static TrimInteriorColorListResponseDto from(Map<ModelInteriorColor, Integer> interiorColorInfos) {
        List<TrimInteriorColorDto> interiorColorDtos = interiorColorInfos.entrySet().stream()
                .map(entry -> TrimInteriorColorDto.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return TrimInteriorColorListResponseDto.builder()
                .trimInteriorColorDtoList(interiorColorDtos)
                .build();
    }

}
