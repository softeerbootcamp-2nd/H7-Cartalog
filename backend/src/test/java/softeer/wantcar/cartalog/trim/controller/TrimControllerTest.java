package softeer.wantcar.cartalog.trim.controller;


import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static softeer.wantcar.cartalog.trim.dto.TrimListResponseDTO.*;

@DisplayName("트림 도메인 컨트롤러 테스트")
class TrimControllerTest {
    SoftAssertions softAssertions;
    TrimController trimController = new TrimController();
    static String imageServerPath = "example-url";

    void setUp() {
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("트림 모델 목록 조회 테스트")
    class getTrimModelListTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 목록을 반환해야 한다.")
        void returnDtoHasTrimsWhenGetTrimsByExistModel() {
            //given
            List<TrimDto> trimDtos = List.of(getTrimDto("Exclusive", "기본기를 갖춘 베이직한 팰리세이드", 40440000, 100000000),
                    getTrimDto("Le Blanc", "실용적인 사양의 경제적인 팰리세이드", 43460000, 100000000),
                    getTrimDto("Prestige", "합리적인 필수 사양을 더한 팰리세이드", 47720000, 100000000),
                    getTrimDto("Calligraphy", "프리미엄한 경험을 선사하는 팰리세이드", 52540000, 100000000));

            TrimListResponseDTO expectResponse = new TrimListResponseDTO("팰리세이드", trimDtos);

            //when
            TrimListResponseDTO realResponse = trimController.searchTrimList(1L).getBody();

            //then
            assertThat(realResponse).isEqualTo(expectResponse);
        }

        @Test
        @DisplayName("존재하지 않는 모델 식별자로 트림 목록 요청시 404 상태를 반환해야 한다.")
        void returnStatusCode404WhenGetTrimsByNotExistModel() {
            //given
            //when
            ResponseEntity<TrimListResponseDTO> response = trimController.searchTrimList(-1L);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    private static List<HMGDataDto> getHmgDataDtos() {
        return List.of(new HMGDataDto("안전 하차 정보", "42회", "15,000km 당"),
                new HMGDataDto("후측방 충돌 경고", "42회", "15,000km 당"),
                new HMGDataDto("후방 교차 충돌방지 보조", "42회", "15,000km 당"));
    }

    private static TrimDto getTrimDto(String name, String description, int minPrice, int maxPrice) {
        List<ModelTypeDto> modelTypeDtos = List.of(new ModelTypeDto("파워트레인", new OptionDto(1L, "디젤 2.2", 0)),
                new ModelTypeDto("구동방식", new OptionDto(3L, "2WD", 0)),
                new ModelTypeDto("바디타입", new OptionDto(5L, "7인승", 0)));

        DefaultTrimInfoDto defaultTrimInfo = new DefaultTrimInfoDto(modelTypeDtos, "A2B", "A22");

        return TrimDto.builder()
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
