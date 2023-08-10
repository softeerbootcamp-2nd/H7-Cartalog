package softeer.wantcar.cartalog.model.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.model.dto.ModelPerformanceDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepositoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("모델 도메인 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
class ModelControllerTest {
    SoftAssertions softAssertions;
    ModelController modelController;
    ModelOptionQueryRepository modelOptionQueryRepository;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        modelOptionQueryRepository = mock(ModelOptionQueryRepositoryImpl.class);
        modelController = new ModelController(modelOptionQueryRepository);
    }

    @Nested
    @DisplayName("모델타입 목록 조회 테스트")
    class getModelTypeListTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 모델 식별자에 해당하는 모델의 모델 타입 리스트를 반환한다.")
        void returnDtoHasModelTypeOfBasicModelIdWhenGetModelTypeByExistBasicModelId() {
            //given
            Long basicModelId = 1L;
            ModelTypeListResponseDto returnDto = mock(ModelTypeListResponseDto.class);
            when(modelOptionQueryRepository.findByModelTypeOptionsByBasicModelId(basicModelId)).thenReturn(returnDto);

            //when
            ResponseEntity<ModelTypeListResponseDto> response = modelController.searchModelType(basicModelId);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();

        }

        @Test
        @DisplayName("존재하지 않는 모델의 식별자로 조회할 경우 404 상태를 반환해야 한다.")
        void returnStatusCode404WhenGetModelTypeByExistModelId() {
            //given
            Long basicModelId = -1L;
            when(modelOptionQueryRepository.findByModelTypeOptionsByBasicModelId(basicModelId)).thenReturn(null);

            //when
            ResponseEntity<ModelTypeListResponseDto> response = modelController.searchModelType(basicModelId);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("모델 성능 조회 테스트")
    class getModelPerformanceTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 배기량과 평균연비를 반환해야 한다.")
        void returnDisplacementAndFuelEfficiencyWithStatusCode200() {
            //given
            ModelPerformanceDto expectResponse = new ModelPerformanceDto(2199f, 12f);

            //when
            ModelPerformanceDto realResponse = modelController.getModelPerformance(1L, 3L, 5L).getBody();

            //then
            assertThat(realResponse).isNotNull();
            softAssertions.assertThat(realResponse.getDisplacement()).isEqualTo(2199f);
            softAssertions.assertThat(realResponse.getFuelEfficiency()).isEqualTo(12f);
            softAssertions.assertThat(realResponse).isEqualTo(expectResponse);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 모델의 식별자로 조회할 경우 404 에러를 반환해야 한다.")
        void returnStatusCode404WhenGetNotExistModelId() {
            //given
            //when
            ResponseEntity<ModelPerformanceDto> realResponse = modelController.getModelPerformance(-1L, 3L, 5L);

            //then
            assertThat(realResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("존재하지 않는 파워트레인 식별자로 조회할 경우 404 에러를 반환해야 한다.")
        void returnStatusCode404WhenGetNotExistPowerTrainId() {
            //given
            //when
            ResponseEntity<ModelPerformanceDto> realResponse = modelController.getModelPerformance(1L, -1L, 5L);

            //then
            assertThat(realResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("존재하지 않는 구동방식 식별자로 조회할 경우 404 에러를 반환해야 한다.")
        void returnStatusCode404WhenGetNotExistWDId() {
            //given
            //when
            ResponseEntity<ModelPerformanceDto> realResponse = modelController.getModelPerformance(1L, 3L, -1L);

            //then
            assertThat(realResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
