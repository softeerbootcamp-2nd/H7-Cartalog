package softeer.wantcar.cartalog.estimate.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;
import softeer.wantcar.cartalog.estimate.service.SimilarityService;

import java.util.List;

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
                    .similarEstimates(List.of())
                    .build();
            when(similarityService.getSimilarEstimateDtoList(anyLong()))
                    .thenReturn(expectResponse);

            //when
            ResponseEntity<SimilarEstimateResponseDto> actualResponse = similarityController.findSimilarEstimates(1L);

            //then
            softAssertions.assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(actualResponse.getBody()).isEqualTo(expectResponse);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자를 전달하면 404 상태를 반환한다")
        void return404() {
            //given
            when(similarityService.getSimilarEstimateDtoList(anyLong()))
                    .thenReturn(null);

            //when
            ResponseEntity<SimilarEstimateResponseDto> actualResponse = similarityController.findSimilarEstimates(1L);

            //then
            softAssertions.assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(actualResponse.getBody()).isNull();
            softAssertions.assertAll();
        }
    }
}
