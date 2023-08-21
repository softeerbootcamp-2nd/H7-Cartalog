package softeer.wantcar.cartalog.similarity.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.similarity.controller.SimilarityController;
import softeer.wantcar.cartalog.similarity.dto.SimilarEstimateCountResponseDto;
import softeer.wantcar.cartalog.similarity.dto.SimilarEstimateResponseDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateCountDto;
import softeer.wantcar.cartalog.similarity.service.SimilarityService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("유사 견적 관련 Controller 테스트")
class SimilarityControllerTest {
    SimilarityService similarityService;
    SimilarityController similarityController;
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        similarityService = mock(SimilarityService.class);
        similarityController = new SimilarityController(similarityService);
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("findSimilarEstimates 테스트")
    class findSimilarEstimatesTest {
        @Test
        @DisplayName("존재하는 견적 식별자를 전달하면 200 상태와 함께 유사 견적들을 반환해야 한다")
        void returnStatus200AndSimilarEstimates() {
            //given
            SimilarEstimateResponseDto expectResponse = SimilarEstimateResponseDto.builder()
                    .build();
            when(similarityService.getSimilarEstimateInfo(anyLong(), anyLong()))
                    .thenReturn(expectResponse);

            //when
            ResponseEntity<SimilarEstimateResponseDto> actualResponse = similarityController.findSimilarEstimates(1L, 2L);

            //then
            softAssertions.assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(actualResponse.getBody()).isEqualTo(expectResponse);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자를 전달하면 404 상태를 반환한다")
        void return404() {
            //given
            when(similarityService.getSimilarEstimateInfo(anyLong(), anyLong()))
                    .thenReturn(null);

            //when
            ResponseEntity<SimilarEstimateResponseDto> actualResponse = similarityController.findSimilarEstimates(1L, 1L);

            //then
            softAssertions.assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(actualResponse.getBody()).isNull();
            softAssertions.assertAll();
        }
    }

    @Nested
    @DisplayName("findSimilarEstimateCounts 테스트")
    class findSimilarEstimateCountsTest {
        @Test
        @DisplayName("존재하는 견적 식별자를 제공할 경우 200 상태와 함께 해당 견적 개수 및 유사 견적 개수들을 반환한다")
        void returnStatus200AndEstimateCounts() {
            //given
            SimilarEstimateCountResponseDto expectResponse = new SimilarEstimateCountResponseDto(3L,
                    List.of(new EstimateCountDto(2L, 3L),
                            new EstimateCountDto(3L, 4L)));
            when(similarityService.getSimilarEstimateCounts(anyLong()))
                    .thenReturn(expectResponse);
            //when
            ResponseEntity<SimilarEstimateCountResponseDto> similarEstimateCounts =
                    similarityController.findSimilarEstimateCounts(1L);

            //then
            softAssertions.assertThat(similarEstimateCounts.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(similarEstimateCounts.getBody()).isEqualTo(expectResponse);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자를 제공할 경우 IllegalArgumentException을 발생시킨다")
        void throwIllegalArgumentException() {
            //given
            when(similarityService.getSimilarEstimateCounts(anyLong()))
                    .thenThrow(IllegalArgumentException.class);

            //when
            //then
            assertThatThrownBy(() -> similarityController.findSimilarEstimateCounts(1L))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
