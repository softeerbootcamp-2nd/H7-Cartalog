package softeer.wantcar.cartalog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.dto.ModelTypeListResponseDto;

import java.util.List;

@Slf4j
@RestController(value = "/models/{modelId}")
public class ModelController {
    public ModelTypeListResponseDto searchModelType() {
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

        return ModelTypeListResponseDto.builder()
                .modelTypes(List.of(powerTrains, wheelDrives, bodyTypes))
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get7Seat() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .name("7인승")
                .price(0)
                .description("기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, 3열 탑승객의 승하차가 편리합니다")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get8Seat() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .name("8인승")
                .price(0)
                .description("1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get2WD() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .name("2WD")
                .price(0)
                .description("엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다\n" +
                             "차체가 가벼워 연료 효율이 높습니다")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto get4WD() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .name("4WD")
                .price(2370000)
                .description("전자식 상시 4륜 구동 시스템 입니다\n" +
                             "도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다")
                .build();
    }

    private ModelTypeListResponseDto.OptionDto getGasolineEngine() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .name("디젤 2.2")
                .price(1480000)
                .description("높은 토크로 파워풀한 드라이빙이 가능하며, 차급대비 연비 효율이 우수합니다")
                .chosen(38)
                .image("example-url/palisade/le-blanc/options/gasoline3.8_s.jpg")
                .hmgData(List.of(new ModelTypeListResponseDto.HMGDataDto("최고출력(PS/rpm)", "202/3,800"),
                        new ModelTypeListResponseDto.HMGDataDto("최대토크(kgf-m/rpm)", "45.0/1,750~2,750")))
                .build();
    }

    private static ModelTypeListResponseDto.OptionDto getDieselEngine() {
        return ModelTypeListResponseDto.OptionDto.builder()
                .name("디젤 2.2")
                .price(0)
                .chosen(38)
                .description("고마력의 우수한 가속 성능을 확보하여, 넉넉하고 안정감 있는 주행이 가능합니다\n" +
                             "엔진의 진동이 적어 편안하고 조용한 드라이빙 감성을 제공합니다")
                .image("example-url/palisade/le-blanc/options/dieselengine2.2_s.jpg")
                .hmgData(List.of(new ModelTypeListResponseDto.HMGDataDto("최고출력(PS/rpm)", "295/60,00"),
                        new ModelTypeListResponseDto.HMGDataDto("최대토크(kgf-m/rpm)", "36.2/5,200")))
                .build();
    }
}
