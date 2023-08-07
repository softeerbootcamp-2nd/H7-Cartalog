package softeer.wantcar.cartalog.trim.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDTO;

import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/models/trims")
public class TrimController {
    @Value("${env.imageServerPath}")
    private String imageServerPath = "example-url";

    @GetMapping("")
    public ResponseEntity<TrimListResponseDTO> searchTrimList(@PathParam("modelId") Long modelId) {
        if (modelId < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<TrimListResponseDTO.TrimDto> trimDtos = List.of(
                getTrimDto("Exclusive", "기본기를 갖춘 베이직한 팰리세이드", 40440000, 100000000),
                getTrimDto("Le Blanc", "실용적인 사양의 경제적인 팰리세이드", 43460000, 100000000),
                getTrimDto("Prestige", "합리적인 필수 사양을 더한 팰리세이드", 47720000, 100000000),
                getTrimDto("Calligraphy", "프리미엄한 경험을 선사하는 팰리세이드", 52540000, 100000000));

        TrimListResponseDTO trimListResponseDTO = new TrimListResponseDTO("팰리세이드", trimDtos);

        return new ResponseEntity<>(trimListResponseDTO, HttpStatus.OK);
    }

    private static List<HMGDataDto> getHmgDataDtos() {
        return List.of(new HMGDataDto("안전 하차 정보", "42회", "15,000km 당"),
                new HMGDataDto("후측방 충돌 경고", "42회", "15,000km 당"),
                new HMGDataDto("후방 교차 충돌방지 보조", "42회", "15,000km 당"));
    }

    private TrimListResponseDTO.TrimDto getTrimDto(String name, String description, int minPrice, int maxPrice) {
        List<TrimListResponseDTO.ModelTypeDto> modelTypeDtos = List.of(
                new TrimListResponseDTO.ModelTypeDto("파워트레인", new TrimListResponseDTO.OptionDto(1L, "디젤 2.2", 0)),
                new TrimListResponseDTO.ModelTypeDto("구동방식", new TrimListResponseDTO.OptionDto(3L, "2WD", 0)),
                new TrimListResponseDTO.ModelTypeDto("바디타입", new TrimListResponseDTO.OptionDto(5L, "7인승", 0)));

        TrimListResponseDTO.DefaultTrimInfoDto defaultTrimInfo =
                new TrimListResponseDTO.DefaultTrimInfoDto(modelTypeDtos, "A2B", "A22");

        return TrimListResponseDTO.TrimDto.builder()
                .name(name)
                .description(description)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .exteriorImage(imageServerPath + "/example-exterior-image")
                .interiorImage(imageServerPath + "/example-interior-image")
                .wheelImage(imageServerPath + "/example-wheel-image")
                .hmgData(getHmgDataDtos())
                .defaultInfo(defaultTrimInfo)
                .build();
    }
}
