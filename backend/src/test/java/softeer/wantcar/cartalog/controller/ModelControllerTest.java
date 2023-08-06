package softeer.wantcar.cartalog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.dto.HMGDataDto;
import softeer.wantcar.cartalog.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.dto.TrimListResponseDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static softeer.wantcar.cartalog.dto.TrimListResponseDTO.*;

@DisplayName("모델 도메인 컨트롤러 테스트")
class ModelControllerTest {
    ModelController modelController = new ModelController();

    @Nested
    @DisplayName("트림 목록을 조회할 경우")
    class getTrimsWithOutOptionSelect {
        @Test
        @DisplayName("트림 목록을 담은 DTO를 반환한다")
        void returnDtoHasTrims() {
            //given
            List<TrimDto> trimDtos = List.of(getTrimDto("Exclusive", "기본기를 갖춘 베이직한 팰리세이드", 40440000, 100000000),
                    getTrimDto("Le Blanc", "실용적인 사양의 경제적인 팰리세이드", 43460000, 100000000),
                    getTrimDto("Prestige", "합리적인 필수 사양을 더한 팰리세이드", 47720000, 100000000),
                    getTrimDto("Calligraphy", "프리미엄한 경험을 선사하는 팰리세이드", 52540000, 100000000));

            TrimListResponseDTO expectResponse = builder()
                    .modelName("팰리세이드")
                    .trims(trimDtos)
                    .build();

            //when
            TrimListResponseDTO realResponse = modelController.searchTrimList().getBody();

            //then
            assertThat(realResponse).isEqualTo(expectResponse);
        }
    }

    @Nested
    @DisplayName("존재하는 모델의 식별자를 통해 모델 타입을 조회할 경우")
    class getModelTypeByExistModelId {
        @Test
        @DisplayName("모델 식별자에 해당하는 모델의 모델 타입들을 담은 DTO를 반환한다")
        void returnDtoHasModelTypeOfModelId() {
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

        @Nested
        @DisplayName("존재하지 않는 모델의 식별자를 조회할 경우")
        class getModelTypeByNotExistModelId {
            @Test
            @DisplayName("상태코드 404를 반환한다")
            void returnStatusCode404() {
                //given
                //when
                ResponseEntity<ModelTypeListResponseDto> response = modelController.searchModelType(100L);

                //then
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            }
        }
    }

    private ModelTypeListResponseDto.OptionDto get7Seat() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .id(5L)
                .name("7인승")
                .price(0)
                .description("기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, 3열 탑승객의 승하차가 편리합니다")
                .imageUrl("example-url/palisade/le-blanc/options/7_seat.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get8Seat() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .id(6L)
                .name("8인승")
                .price(0)
                .description("1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다")
                .imageUrl("example-url/palisade/le-blanc/options/8_seat.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get2WD() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .id(3L)
                .name("2WD")
                .price(0)
                .description("엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다\n" +
                             "차체가 가벼워 연료 효율이 높습니다")
                .imageUrl("example-url/palisade/le-blanc/options/2wd.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get4WD() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .id(4L)
                .name("4WD")
                .price(2370000)
                .description("전자식 상시 4륜 구동 시스템 입니다\n" +
                             "도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다")
                .imageUrl("example-url/palisade/le-blanc/options/4wd.jpg")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto getGasolineEngine() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .id(1L)
                .name("디젤 2.2")
                .price(1480000)
                .description("높은 토크로 파워풀한 드라이빙이 가능하며, 차급대비 연비 효율이 우수합니다")
                .chosen(38)
                .imageUrl("example-url/palisade/le-blanc/options/gasoline3.8_s.jpg")
                .hmgData(List.of(new HMGDataDto("최고출력", "202/3,800", "PS/rpm"),
                        new HMGDataDto("최대토크", "45.0/1,750~2,750", "kgf-m/rpm")))
                .build();
    }

    private static ModelTypeListResponseDto.OptionDto getDieselEngine() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .name("디젤 2.2")
                .price(0)
                .chosen(38)
                .description("고마력의 우수한 가속 성능을 확보하여, 넉넉하고 안정감 있는 주행이 가능합니다\n" +
                             "엔진의 진동이 적어 편안하고 조용한 드라이빙 감성을 제공합니다")
                .imageUrl("example-url/palisade/le-blanc/options/dieselengine2.2_s.jpg")
                .hmgData(List.of(new HMGDataDto("최고출력", "295/60,00", "PS/rpm"),
                        new HMGDataDto("최대토크", "36.2/5,200", "kgf-m/rpm")))
                .build();
    }

    private static List<HMGDataDto> getHmgDataDtos() {
        return List.of(new HMGDataDto("안전 하차 정보", "42회", "15,000km 당"),
                new HMGDataDto("후측방 충돌 경고", "42회", "15,000km 당"),
                new HMGDataDto("후방 교차 충돌방지 보조", "42회", "15,000km 당"));
    }

    private static TrimListResponseDTO.TrimDto getTrimDto(String name, String description, int minPrice, int maxPrice) {
        return TrimListResponseDTO.TrimDto.builder()
                .name(name)
                .description(description)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .hmgData(getHmgDataDtos())
                .build();
    }
}
