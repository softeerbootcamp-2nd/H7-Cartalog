package softeer.wantcar.cartalog.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("색상 도메인 컨트롤러 테스트")
class ColorControllerTest {

    SoftAssertions softAssertions;
    ColorController colorController = new ColorController();

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("트림 외장 색상 목록 조회 테스트")
    class searchTrimExteriorColor {

        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 외장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            Long id = 1L;
            String name = "어비스 블랙 펄";
            String code = "141423";
            int price = 0;
            int chosen = 38;
            TrimExteriorColor color = TrimExteriorColor.builder()
                    .id(id)
                    .name(name)
                    .code(code)
                    .price(price)
                    .chosen(chosen)
                    .build();
            TrimExteriorColorListResponseDto expectResponse = TrimExteriorColorListResponseDto.from(List.of(color));

            //when
            TrimExteriorColorListResponseDto responseDto = colorController.trimExteriorColorList(id);

            //then
            List<TrimExteriorColorListResponseDto.TrimExteriorColorDto> trimExteriorColorDtoList = responseDto.getTrimExteriorColorDtoList();
            assertThat(trimExteriorColorDtoList.size()).isEqualTo(1);

            TrimExteriorColorListResponseDto.TrimExteriorColorDto trimExteriorColorDto = trimExteriorColorDtoList.get(0);

            softAssertions.assertThat(trimExteriorColorDto.getId()).isEqualTo(id);
            softAssertions.assertThat(trimExteriorColorDto.getName()).isEqualTo(name);
            softAssertions.assertThat(trimExteriorColorDto.getCode()).isEqualTo("#" + code);
            softAssertions.assertThat(trimExteriorColorDto.getPrice()).isEqualTo(price);
            softAssertions.assertThat(trimExteriorColorDto.getChosen()).isEqualTo(chosen);
        }

        // TODO
        @DisplayName("존재하지 않는 트림 식별자로 색상 요청시 404 상태를 반환해야 한다.")
        void notFound() {
            //given

            //when

            //then

        }

    }

}