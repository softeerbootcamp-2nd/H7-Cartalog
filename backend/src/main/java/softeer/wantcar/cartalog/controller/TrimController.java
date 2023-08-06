package softeer.wantcar.cartalog.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.dto.HMGDataDto;
import softeer.wantcar.cartalog.dto.TrimListResponseDTO;

import java.util.List;

@Slf4j
@RestController("/models/trims")
public class TrimController {
    public ResponseEntity<TrimListResponseDTO> searchTrimList() {
        List<TrimListResponseDTO.TrimDto> trimDtos = List.of(getTrimDto("Exclusive", "기본기를 갖춘 베이직한 팰리세이드", 40440000, 100000000),
                getTrimDto("Le Blanc", "실용적인 사양의 경제적인 팰리세이드", 43460000, 100000000),
                getTrimDto("Prestige", "합리적인 필수 사양을 더한 팰리세이드", 47720000, 100000000),
                getTrimDto("Calligraphy", "프리미엄한 경험을 선사하는 팰리세이드", 52540000, 100000000));

        TrimListResponseDTO trimListResponseDTO = TrimListResponseDTO.builder()
                .modelName("팰리세이드")
                .trims(trimDtos)
                .build();

        return new ResponseEntity<>(trimListResponseDTO, HttpStatus.OK);
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
