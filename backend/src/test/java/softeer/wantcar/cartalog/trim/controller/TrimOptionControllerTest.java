package softeer.wantcar.cartalog.trim.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;

import java.util.List;


@DisplayName("트림 옵션 도메인 테스트")
public class TrimOptionControllerTest {
    SoftAssertions softAssertions;
    TrimOptionController trimOptionController;
    static String imageServerPath = "example-url";

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("트림 옵션 목록 조회 테스트")
    class getTrimOptionListTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 옵션 정보 리스트를 반환한다.")
        void returnOptionInfoListWithStatusCode200() {
            //given
            //when
            TrimOptionListResponseDto realResponse = trimOptionController.getOptionInfos(1L).getBody();

            //then
            softAssertions.assertThat(realResponse.getMultipleSelectParentCategory())
                    .containsAll(List.of("상세 품목", "악세서리"));
            softAssertions.assertThat(realResponse.getDefaultOptions())
                    .contains(getTrimOptionDto(11L,
                            "2열 통풍시트",
                            "상세품목",
                            "시트",
                            "2_cool_seat.jpg",
                            350000));
            softAssertions.assertThat(realResponse.getSelectOptions())
                    .contains(getTrimOptionDto(5L,
                            "디젤 2.2 엔진",
                            null,
                            "파워트레인/성능",
                            "/palisade/le-blanc/options/dieselengine2.2_s.jpg",
                            0));
            softAssertions.assertAll();
        }
    }

    private static TrimOptionListResponseDto.TrimOptionDto getTrimOptionDto(
            Long id, String name, String parentCategory, String childCategory, String imageUrl, int price
    ) {
        return TrimOptionListResponseDto.TrimOptionDto.builder()
                .id(id)
                .name(name)
                .parentCategory(parentCategory)
                .childCategory(childCategory)
                .imageUrl(imageServerPath + imageUrl)
                .price(price)
                .chosen(38)
                .hashTags(List.of("장거리 운전"))
                .build();
    }
}
