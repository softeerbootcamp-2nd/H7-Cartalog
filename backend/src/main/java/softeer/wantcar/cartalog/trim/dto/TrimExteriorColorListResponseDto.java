package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import softeer.wantcar.cartalog.global.annotation.TestMethod;
import softeer.wantcar.cartalog.global.utils.CompareUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TrimExteriorColorListResponseDto {
    @Singular("trimExteriorColorDto")
    List<TrimExteriorColorDto> trimExteriorColorDtoList;

    @TestMethod
    public boolean hasColor(List<String> selectableLeBlancExteriorColors) {
        List<String> codes = trimExteriorColorDtoList.stream()
                .map(TrimExteriorColorDto::getCode)
                .collect(Collectors.toUnmodifiableList());
        return CompareUtils.equalAndAllContain(selectableLeBlancExteriorColors, codes);
    }

    @TestMethod
    public boolean startWithUrl(String url) {
        return trimExteriorColorDtoList.stream()
                .allMatch(trimExteriorColorDto -> trimExteriorColorDto.carImageUrl.startsWith(url));
    }

    @Getter
    @Builder
    public static class TrimExteriorColorDto {
        private String code;
        private String name;
        private String colorImageUrl;
        private String carImageUrl;
        private int price;
        private int chosen;
    }
}
