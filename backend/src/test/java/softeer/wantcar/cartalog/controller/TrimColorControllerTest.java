package softeer.wantcar.cartalog.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;
import softeer.wantcar.cartalog.trim.service.MockTrimColorService;
import softeer.wantcar.cartalog.trim.service.TrimColorService;
import softeer.wantcar.cartalog.trim.controller.TrimColorController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("색상 도메인 컨트롤러 테스트")
class TrimColorControllerTest {

    SoftAssertions softAssertions;
    TrimColorService trimColorService;
    TrimColorController trimColorController;


    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("트림 외장 색상 목록 조회 테스트")
    class searchTrimExteriorColor {

        @BeforeEach
        void setUp() {
            trimColorService = new MockTrimColorService();
            trimColorController = new TrimColorController(trimColorService);
        }

        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 외장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            Long trimId = 1L;
            ModelExteriorColor mockData = ((MockTrimColorService) trimColorService).getMockModelExteriorColor();

            //when
            ResponseEntity<TrimExteriorColorListResponseDto> responseEntity = trimColorController.trimExteriorColorList(trimId);

            //then
            TrimExteriorColorListResponseDto responseDto = responseEntity.getBody();
            assertThat(responseDto).isNotNull();
            List<TrimExteriorColorListResponseDto.TrimExteriorColorDto> trimExteriorColorDtoList = responseDto.getTrimExteriorColorDtoList();
            assertThat(trimExteriorColorDtoList.size()).isEqualTo(1);

            TrimExteriorColorListResponseDto.TrimExteriorColorDto trimExteriorColorDto = trimExteriorColorDtoList.get(0);

            softAssertions.assertThat(trimExteriorColorDto.getId()).isEqualTo(mockData.getColor().getId());
            softAssertions.assertThat(trimExteriorColorDto.getName()).isEqualTo(mockData.getColor().getName());
            softAssertions.assertThat(trimExteriorColorDto.getColor()).isEqualTo("#" + mockData.getColor().getCode());
            softAssertions.assertThat(trimExteriorColorDto.getPrice()).isEqualTo(mockData.getPrice());
            softAssertions.assertThat(trimExteriorColorDto.getChosen()).isEqualTo(38);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자로 색상 요청시 404 상태를 반환해야 한다.")
        void notFound() {
            //given
            //when
            ResponseEntity<TrimExteriorColorListResponseDto> responseEntity = trimColorController.trimExteriorColorList(100L);

            //then
            softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(responseEntity.getBody()).isNull();
            softAssertions.assertAll();
        }

    }

    @Nested
    @DisplayName("트림 내장 색상 목록 조회 테스트")
    class searchTrimInteriorColor {
        @BeforeEach
        void setUp() {
            trimColorService = new MockTrimColorService();
            trimColorController = new TrimColorController(trimColorService);
        }

        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 내장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            Long trimId = 1L;
            Long exteriorColorId = 1L;
            ModelInteriorColor mockData = ((MockTrimColorService) trimColorService).getMockModelInteriorColor();

            //when
            ResponseEntity<TrimInteriorColorListResponseDto> responseEntity = trimColorController.trimInteriorColorList(trimId, exteriorColorId);

            //then
            TrimInteriorColorListResponseDto responseDto = responseEntity.getBody();
            assertThat(responseDto).isNotNull();
            List<TrimInteriorColorListResponseDto.TrimInteriorColorDto> trimInteriorColorDtoList = responseDto.getTrimInteriorColorDtoList();
            assertThat(trimInteriorColorDtoList.size()).isEqualTo(1);

            TrimInteriorColorListResponseDto.TrimInteriorColorDto trimInteriorColorDto = trimInteriorColorDtoList.get(0);

            softAssertions.assertThat(trimInteriorColorDto.getId()).isEqualTo(mockData.getId());
            softAssertions.assertThat(trimInteriorColorDto.getName()).isEqualTo(mockData.getName());
            softAssertions.assertThat(trimInteriorColorDto.getColorImageUrl()).isEqualTo(mockData.getImageUrl());
            softAssertions.assertThat(trimInteriorColorDto.getPrice()).isEqualTo(mockData.getPrice());
            softAssertions.assertThat(trimInteriorColorDto.getChosen()).isEqualTo(38);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자로 색상 요청시 404 상태를 반환해야 한다.")
        void notFound() {
            //given

            //when
            ResponseEntity<TrimInteriorColorListResponseDto> responseEntity = trimColorController.trimInteriorColorList(100L, 100L);

            //then
            softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(responseEntity.getBody()).isNull();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("외장 색상 누락시 404 상태를 반환해야 한다.")
        void notFound2() {
            //given

            //when
            ResponseEntity<TrimInteriorColorListResponseDto> responseEntity = trimColorController.trimInteriorColorList(100L, null);

            //then
            softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(responseEntity.getBody()).isNull();
            softAssertions.assertAll();
        }

    }

}
