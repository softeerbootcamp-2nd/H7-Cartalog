package softeer.wantcar.cartalog.estimate.controller;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.dto.EstimateResponseDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.service.EstimateService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("견적서 컨트롤러 테스트")
@ExtendWith(SoftAssertionsExtension.class)
class EstimateControllerTest {
    @InjectSoftAssertions
    SoftAssertions softAssertions;
    EstimateQueryRepository estimateQueryRepository;
    EstimateService estimateService;
    EstimateController estimateController;

    @BeforeEach
    void setUp() {
        estimateQueryRepository = mock(EstimateQueryRepository.class);
        estimateService = mock(EstimateService.class);
        estimateController = new EstimateController(estimateQueryRepository, estimateService);
    }

    @Nested
    @DisplayName("평균 가격 조회 테스트")
    class getDistributionTest {
        @Test
        @DisplayName("평균 가격을 반환해야 한다.")
        void test() {
            //given
            long average = 1000;
            when(estimateQueryRepository.findAveragePrice(anyLong())).thenReturn(average);

            //when
            ResponseEntity<Long> response = estimateController.getDistribution(anyLong());

            //then
            softAssertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(response.getBody()).isEqualTo(average);
        }
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
            ResponseEntity<Long> response = estimateController.registerOrGetEstimateId(requestDto);

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
            //then
            assertThatThrownBy(() -> estimateController.registerOrGetEstimateId(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("견적서 조회 테스트")
    class getEstimateTest {
        @Test
        @DisplayName("유효한 견적서 식별자를 전달헀을 때 견적서 정보를 정상적으로 반환해야 한다.")
        void success() {
            //given
            EstimateResponseDto requestDto = mock(EstimateResponseDto.class);
            when(estimateService.findEstimateByEstimateId(anyLong())).thenReturn(requestDto);

            //when
            ResponseEntity<EstimateResponseDto> response = estimateController.getEstimate(anyLong());

            //then
            softAssertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(response.getBody()).isEqualTo(requestDto);
        }

        @Test
        @DisplayName("존재하지 않은 견적서 식별자를 전달헀을 때 견적서 정보를 정상적으로 반환해야 한다.")
        void returnNotFound() {
            //given
            when(estimateService.findEstimateByEstimateId(anyLong())).thenThrow(IllegalArgumentException.class);

            //when
            ResponseEntity<EstimateResponseDto> response = estimateController.getEstimate(anyLong());

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
