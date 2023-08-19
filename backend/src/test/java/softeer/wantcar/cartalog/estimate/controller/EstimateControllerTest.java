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
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("견적서 컨트롤러 테스트")
@ExtendWith(SoftAssertionsExtension.class)
class EstimateControllerTest {
    @InjectSoftAssertions
    SoftAssertions softAssertions;
    EstimateQueryRepository estimateQueryRepository;
    EstimateController estimateController;

    @BeforeEach
    void setUp() {
        estimateQueryRepository = mock(EstimateQueryRepository.class);
        estimateController = new EstimateController(estimateQueryRepository);
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

}