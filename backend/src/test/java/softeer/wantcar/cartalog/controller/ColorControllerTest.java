package softeer.wantcar.cartalog.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;
import softeer.wantcar.cartalog.service.ColorService;
import softeer.wantcar.cartalog.service.MockColorService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("색상 도메인 컨트롤러 테스트")
class ColorControllerTest {

    SoftAssertions softAssertions;
    ColorService colorService = new MockColorService();
    ColorController colorController = new ColorController(colorService);


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
            colorService = new MockColorService();
            TrimExteriorColor mockData = ((MockColorService) colorService).getMockData();

            //when
            ResponseEntity<TrimExteriorColorListResponseDto> responseEntity = colorController.trimExteriorColorList(mockData.getId());

            //then
            TrimExteriorColorListResponseDto responseDto = responseEntity.getBody();
            assertThat(responseDto).isNotNull();
            List<TrimExteriorColorListResponseDto.TrimExteriorColorDto> trimExteriorColorDtoList = responseDto.getTrimExteriorColorDtoList();
            assertThat(trimExteriorColorDtoList.size()).isEqualTo(1);

            TrimExteriorColorListResponseDto.TrimExteriorColorDto trimExteriorColorDto = trimExteriorColorDtoList.get(0);

            softAssertions.assertThat(trimExteriorColorDto.getId()).isEqualTo(mockData.getId());
            softAssertions.assertThat(trimExteriorColorDto.getName()).isEqualTo(mockData.getName());
            softAssertions.assertThat(trimExteriorColorDto.getCode()).isEqualTo("#" + mockData.getCode());
            softAssertions.assertThat(trimExteriorColorDto.getPrice()).isEqualTo(mockData.getPrice());
            softAssertions.assertThat(trimExteriorColorDto.getChosen()).isEqualTo(mockData.getChosen());
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