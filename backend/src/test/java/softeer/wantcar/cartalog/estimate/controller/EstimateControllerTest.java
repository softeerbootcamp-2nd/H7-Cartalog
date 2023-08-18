package softeer.wantcar.cartalog.estimate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.service.EstimateService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EstimateControllerTest {

    EstimateController estimateController;
    EstimateService estimateService;

    @BeforeEach
    public void setUp() {
        estimateService = mock(EstimateService.class);
        estimateController = new EstimateController(estimateService);
    }

    @Nested
    @DisplayName("견적서 등록 조회 테스트")
    class registerOrGetEstimateTest {
        @Test
        @DisplayName("유효한 견적서 식별자를 요청했을때 정상적으로 반환해야 한다.")
        void success() {
            //given
            Long estimateId = 1L;
            EstimateRequestDto requestDto = mock(EstimateRequestDto.class);
            when(estimateService.saveOrFindEstimateId(any())).thenReturn(estimateId);

            //when
            ResponseEntity<Long> response = estimateController.registerOrGetEstimate(requestDto);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(estimateId);
        }

        @Test
        @DisplayName("유효하지 않은 견적서를 전달했을 경우 요청을 거부해야 한다.")
        void failure() {
            //given
            EstimateRequestDto requestDto = mock(EstimateRequestDto.class);
            when(estimateService.saveOrFindEstimateId(any())).thenThrow(IllegalArgumentException.class);

            //when
            ResponseEntity<Long> response = estimateController.registerOrGetEstimate(requestDto);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isNull();
        }
    }

}