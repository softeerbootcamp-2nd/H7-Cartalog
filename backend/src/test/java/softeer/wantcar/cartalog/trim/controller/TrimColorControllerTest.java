package softeer.wantcar.cartalog.trim.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.service.MockTrimColorService;
import softeer.wantcar.cartalog.trim.service.TrimColorService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("색상 도메인 컨트롤러 테스트")
class TrimColorControllerTest {
    SoftAssertions softAssertions;
    TrimColorQueryRepository trimColorQueryRepository;
    TrimColorService trimColorService;
    TrimColorController trimColorController;

    @BeforeEach
    void setUp() {
        trimColorService = new MockTrimColorService();
        softAssertions = new SoftAssertions();
        trimColorQueryRepository = mock(TrimColorQueryRepository.class);
        trimColorController = new TrimColorController(trimColorQueryRepository);
    }

    @Nested
    @DisplayName("트림 외장 색상 목록 조회 테스트")
    class searchTrimExteriorColor {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 외장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            TrimExteriorColorListResponseDto expected = mock(TrimExteriorColorListResponseDto.class);
            when(trimColorQueryRepository.findTrimExteriorColorByTrimId(anyLong())).thenReturn(expected);

            //when
            ResponseEntity<TrimExteriorColorListResponseDto> actual = trimColorController.searchTrimExteriorColorList(anyLong());

            //then
            softAssertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(actual.getBody()).isEqualTo(expected);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자로 색상 요청시 404 상태를 반환해야 한다.")
        void notFound() {
            //given
            when(trimColorQueryRepository.findTrimExteriorColorByTrimId(anyLong())).thenReturn(null);

            //when
            ResponseEntity<TrimExteriorColorListResponseDto> responseEntity = trimColorController.searchTrimExteriorColorList(anyLong());

            //then
            softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(responseEntity.getBody()).isNull();
            softAssertions.assertAll();
        }

    }

    @Nested
    @DisplayName("트림 내장 색상 목록 조회 테스트")
    class searchTrimInteriorColor {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 내장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            TrimInteriorColorListResponseDto expected = mock(TrimInteriorColorListResponseDto.class);
            when(trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(anyLong(), anyString())).thenReturn(expected);

            //when
            ResponseEntity<TrimInteriorColorListResponseDto> actual = trimColorController.searchTrimInteriorColorList(anyLong(), anyString());

            //then
            softAssertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(actual.getBody()).isEqualTo(expected);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자 혹은 외장 색상 코드로 색상 요청시 404 상태를 반환해야 한다.")
        void notFound() {
            //given
            when(trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(anyLong(), anyString())).thenReturn(null);

            //when
            ResponseEntity<TrimInteriorColorListResponseDto> responseEntity = trimColorController.searchTrimInteriorColorList(anyLong(), anyString());

            //then
            softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(responseEntity.getBody()).isNull();
            softAssertions.assertAll();
        }
    }
}
