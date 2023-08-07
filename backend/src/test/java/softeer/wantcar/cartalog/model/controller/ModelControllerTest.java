package softeer.wantcar.cartalog.model.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.model.dto.ModelPerformanceDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("모델 도메인 컨트롤러 테스트")
class ModelControllerTest {
    SoftAssertions softAssertions;
    ModelController modelController = new ModelController();
    static String imageServerPath = "example-url";

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("모델타입 목록 조회 테스트")
    class getModelTypeListTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 모델 식별자에 해당하는 모델의 모델 타입 리스트를 반환한다.")
        void returnDtoHasModelTypeOfModelIdWhenGetModelTypeByExistModelId() {
            //given
            ModelTypeListResponseDto.ModelTypeDto powerTrains = ModelTypeListResponseDto.ModelTypeDto.builder()
                    .type("powerTrain")
                    .options(List.of(getDieselEngine(), getGasolineEngine()))
                    .build();

            ModelTypeListResponseDto.ModelTypeDto wheelDrives = ModelTypeListResponseDto.ModelTypeDto.builder()
                    .type("wheelDrive")
                    .options(List.of(get2WD(), get4WD()))
                    .build();

            ModelTypeListResponseDto.ModelTypeDto bodyTypes = ModelTypeListResponseDto.ModelTypeDto.builder()
                    .type("bodyType")
                    .options(List.of(get7Seat(), get8Seat()))
                    .build();

            ModelTypeListResponseDto expectResponse = ModelTypeListResponseDto.builder()
                    .modelTypes(List.of(powerTrains, wheelDrives, bodyTypes))
                    .build();

            //when
            ModelTypeListResponseDto realResponse = modelController.searchModelType(1L).getBody();

            //then
            assertThat(realResponse).isEqualTo(expectResponse);
        }

        @Test
        @DisplayName("존재하지 않는 모델의 식별자로 조회할 경우 404 상태를 반환해야 한다.")
        void returnStatusCode404WhenGetModelTypeByExistModelId() {
            //given
            //when
            ResponseEntity<ModelTypeListResponseDto> response = modelController.searchModelType(-1L);

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
        void returnStatusCode404WhenGetNotExistWD가Id() {
            //given
            //when
            ResponseEntity<ModelPerformanceDto> realResponse = modelController.getModelPerformance(1L, 3L, -1L);

            //then
            assertThat(realResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    private ModelTypeListResponseDto.OptionDto get7Seat() {
        return ModelTypeListResponseDto.ModelTypeOptionDto.builder()
                .id(5L)
                .name("7인승")
                .price(0)
                .description("기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, 3열 탑승객의 승하차가 편리합니다")
                .imageUrl(imageServerPath + "/palisade/le-blanc/options/7_seat.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get8Seat() {
        return ModelTypeListResponseDto.ModelTypeOptionDto.builder()
                .id(6L)
                .name("8인승")
                .price(0)
                .description("1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다")
                .imageUrl(imageServerPath + "/palisade/le-blanc/options/8_seat.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get2WD() {
        return ModelTypeListResponseDto.ModelTypeOptionDto.builder()
                .id(3L)
                .name("2WD")
                .price(0)
                .description("엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다\n" +
                             "차체가 가벼워 연료 효율이 높습니다")
                .imageUrl(imageServerPath + "/palisade/le-blanc/options/2wd.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get4WD() {
        return ModelTypeListResponseDto.ModelTypeOptionDto.builder()
                .id(4L)
                .name("4WD")
                .price(2370000)
                .description("전자식 상시 4륜 구동 시스템 입니다\n" +
                             "도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다")
                .imageUrl(imageServerPath + "/palisade/le-blanc/options/4wd.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto getGasolineEngine() {
        return ModelTypeListResponseDto.PowerTrainOptionDto.builder()
                .id(1L)
                .name("디젤 2.2")
                .price(1480000)
                .description("높은 토크로 파워풀한 드라이빙이 가능하며, 차급대비 연비 효율이 우수합니다")
                .chosen(38)
                .imageUrl(imageServerPath + "/palisade/le-blanc/options/gasoline3.8_s.jpg")
                .powerTrainHMGData(List.of(
                        new ModelTypeListResponseDto.PowerTrainHMGDataDto("최고출력", 202f, "3,800", "PS/rpm"),
                        new ModelTypeListResponseDto.PowerTrainHMGDataDto("최대토크", 45.0f, "1,750~2,750", "kgf-m/rpm")))
                .build();
    }

    private static ModelTypeListResponseDto.OptionDto getDieselEngine() {
        return ModelTypeListResponseDto.PowerTrainOptionDto.builder()
                .name("디젤 2.2")
                .price(0)
                .chosen(38)
                .description("고마력의 우수한 가속 성능을 확보하여, 넉넉하고 안정감 있는 주행이 가능합니다\n" +
                             "엔진의 진동이 적어 편안하고 조용한 드라이빙 감성을 제공합니다")
                .imageUrl(imageServerPath + "/palisade/le-blanc/options/dieselengine2.2_s.jpg")
                .powerTrainHMGData(List.of(
                        new ModelTypeListResponseDto.PowerTrainHMGDataDto("최고출력", 295f, "6,000", "PS/rpm"),
                        new ModelTypeListResponseDto.PowerTrainHMGDataDto("최대토크", 36.2f, "5,200", "kgf-m/rpm")))
                .build();
    }
}
