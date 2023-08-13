package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;
import softeer.wantcar.cartalog.global.annotation.TestMethod;
import softeer.wantcar.cartalog.global.utils.CompareUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
public class TrimInteriorColorListResponseDto {
    @Singular("trimInteriorColorDto")
    private List<TrimInteriorColorDto> interiorColors;

    @TestMethod
    public boolean hasColor(List<String> selectableLeBlancExteriorColors) {
        List<String> codes = interiorColors.stream()
                .map(TrimInteriorColorListResponseDto.TrimInteriorColorDto::getCode)
                .collect(Collectors.toUnmodifiableList());
        return CompareUtils.equalAndAllContain(selectableLeBlancExteriorColors, codes);
    }

    @TestMethod
    public boolean startWithUrl(String url) {
        return interiorColors.stream()
                .allMatch(trimInteriorColorDto -> trimInteriorColorDto.carImageUrl.startsWith(url));
    }

    @Getter
    @Builder
    public static class TrimInteriorColorDto {
        private String code;
        private String name;
        private String colorImageUrl;
        private String carImageUrl;
        private int price;
        private int chosen;
    }
}
