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
import softeer.wantcar.cartalog.model.dto.EstimateImageDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepositoryImpl;
import softeer.wantcar.cartalog.model.repository.ModelQueryRepository;
import softeer.wantcar.cartalog.model.service.ModelOptionService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("모델 도메인 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
class ModelControllerTest {
    SoftAssertions softAssertions;
    ModelController modelController;
    ModelOptionQueryRepository modelOptionQueryRepository;
    ModelQueryRepository modelQueryRepository;
    ModelOptionService modelOptionService;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        modelOptionQueryRepository = mock(ModelOptionQueryRepositoryImpl.class);
        modelQueryRepository = mock(ModelQueryRepository.class);
        modelOptionService = mock(ModelOptionService.class);
        modelController = new ModelController(modelQueryRepository, modelOptionService);
    }

    @Nested
    @DisplayName("모델타입 목록 조회 테스트")
    class getModelTypeListTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 식별자에 해당하는 모델의 모델 타입 리스트를 반환한다.")
        void returnDtoHasModelTypeOfBasicModelIdWhenGetModelTypeByExistBasicModelId() {
            //given
            Long trimId = 2L;
            ModelTypeListResponseDto returnDto = mock(ModelTypeListResponseDto.class);
            when(modelOptionService.findModelTypeListByTrimId(trimId)).thenReturn(returnDto);

            //when
            ResponseEntity<ModelTypeListResponseDto> response = modelController.searchModelType(trimId);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();

        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자로 조회할 경우 404 상태를 반환해야 한다.")
        void returnStatusCode404WhenGetModelTypeByExistModelId() {
            //given
            Long basicModelId = -1L;
            when(modelOptionService.findModelTypeListByTrimId(basicModelId)).thenThrow(IllegalArgumentException.class);

            //when
            ResponseEntity<ModelTypeListResponseDto> response = modelController.searchModelType(basicModelId);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("외/내장 색상을 통한 차량 외부 옆면 및 내부 사진 조회 테스트")
    class findSideExteriorAndInteriorImageByExteriorAndInteriorColorCode {
        @Test
        @DisplayName("존재하는 식별자로 조회시 200 상태와 함께 외부 옆면 및 내부 사진을 담은 dto를 반환한다.")
        void returnStatus200WithDtoHasSideExteriorAndInteriorImage() {
            //given
            EstimateImageDto expectResponseBody = new EstimateImageDto("sideExteriorImageUrl", "interiorImageUrl");
            when(modelQueryRepository.findCarSideExteriorAndInteriorImage("A", "A"))
                    .thenReturn(expectResponseBody);

            //when
            ResponseEntity<EstimateImageDto> actualResponse = modelController.findSideExteriorAndInteriorImage("A", "A");

            //then
            softAssertions.assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(actualResponse.getBody()).isEqualTo(expectResponseBody);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 식별자로 조회시 404 상태를 반환한다.")
        void returnStatusCode404() {
            //given
            String exteriorColorCode = "A";
            String interiorColorCode = "A";
            when(modelQueryRepository.findCarSideExteriorAndInteriorImage(exteriorColorCode, interiorColorCode))
                    .thenReturn(null);

            //when
            ResponseEntity<EstimateImageDto> actualResponse = modelController.findSideExteriorAndInteriorImage(exteriorColorCode, interiorColorCode);

            //then
            softAssertions.assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(actualResponse.getBody()).isNull();
            softAssertions.assertAll();
        }
    }
}
