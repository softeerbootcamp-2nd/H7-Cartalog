package softeer.wantcar.cartalog.trim.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.OptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;
import softeer.wantcar.cartalog.trim.service.TrimOptionService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DisplayName("트림 옵션 도메인 테스트")
public class TrimOptionControllerTest {
    SoftAssertions softAssertions;
    TrimOptionController trimOptionController;
    TrimOptionService trimOptionService;
    static String imageServerPath = "example-url";

    @BeforeEach
    void setUp() {
        trimOptionService = mock(TrimOptionService.class);
        trimOptionController = new TrimOptionController(trimOptionService);
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("트림 옵션 목록 조회 테스트")
    class getTrimOptionListTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 옵션 정보 리스트를 반환한다.")
        void returnOptionInfoListWithStatusCode200() {
            //given
            TrimOptionListResponseDto expectResponse = TrimOptionListResponseDto.builder()
                    .multipleSelectParentCategory(List.of("A", "B", "C"))
                    .defaultOptions(List.of(getTrimOptionDto("O1"), getTrimOptionDto("O2")))
                    .selectOptions(List.of(getTrimOptionDto("O3"), getTrimOptionDto("P1")))
                    .build();
            when(trimOptionService.getTrimOptionList(1L, 1L))
                    .thenReturn(expectResponse);

            //when
            ResponseEntity<TrimOptionListResponseDto> realResponse = trimOptionController.getOptionInfos(1L, 1L);

            //then
            assert realResponse != null;
            softAssertions.assertThat(realResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(realResponse.getBody()).isEqualTo(expectResponse);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자로 옵션 정보 목록 요청시 404 상태를 반환해야 한다")
        void returnStatusCode404WhenGetOptionInfoListBy() {
            //given
            when(trimOptionService.getTrimOptionList(-1L, 1L))
                    .thenReturn(null);
            //when
            ResponseEntity<TrimOptionListResponseDto> response = trimOptionController.getOptionInfos(-1L, 1L);

            //then
            softAssertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(response.getBody()).isNull();
            softAssertions.assertAll();
        }
    }

    @Nested
    @DisplayName("비패키지 옵션 상세 조회 테스트")
    class getOptionDetailTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 옵션 상세 정보를 반환해야 한다.")
        void returnOptionDetailWithStatusCode200() {
            //given
            //when
            TrimOptionDetailResponseDto realResponse =
                    (TrimOptionDetailResponseDto) trimOptionController.getOptionDetail("O1", 1L).getBody();

            //then
            assert realResponse != null;
            softAssertions.assertThat(realResponse.isPackage()).isFalse();
            softAssertions.assertThat(realResponse.getName()).isEqualTo("2열 통풍시트");
            softAssertions.assertThat(realResponse.getDescription()).isEqualTo("대강 좋다는 내용");
            softAssertions.assertThat(realResponse.getHashTags()).contains("장거리 운전");
            softAssertions.assertThat(realResponse.getHmgData())
                    .contains(new HMGDataDto("주행 중 실제로", "73.2", "15,000km 당"));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("잘못된 양식의 옵션 식별자로 요청할 경우 400 상태 코드를 반환해야 한다.")
        void returnStatusCode404WhenGetOptionDetailByWrongOptionId() {
            //given
            //when
            ResponseEntity<OptionDetailResponseDto> response = trimOptionController.getOptionDetail("X1", 1L);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("존재하지 않는 옵션 식별자로 요청할 경우 404 상태 코드를 반환해야 한다.")
        void returnStatusCode404WhenGetOptionDetailByNotExistOptionId() {
            //given
            //when
            ResponseEntity<OptionDetailResponseDto> response = trimOptionController.getOptionDetail("O-1", 1L);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자로 요청할 경우 404 상태 코드를 반환해야 한다.")
        void returnStatusCode404WhenGetOptionDetailByNotExistTrimId() {
            //given
            //when
            ResponseEntity<OptionDetailResponseDto> response = trimOptionController.getOptionDetail("O1", -1L);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("패키지 옵션 상세 조회 테스트")
    class getPackageDetailTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 패키지 상세 정보를 반환해야 한다.")
        void returnPackageDetailWithStatusCode200() {
            //given
            //when
            TrimPackageDetailResponseDto realResponse =
                    (TrimPackageDetailResponseDto) trimOptionController.getOptionDetail("P1", 1L).getBody();

            //then
            assert realResponse != null;
            softAssertions.assertThat(realResponse.isPackage()).isTrue();
            List<TrimPackageDetailResponseDto.PackageOptionDetailDto> options = realResponse.getOptions();
            softAssertions.assertThat(options)
                    .contains(new TrimPackageDetailResponseDto.PackageOptionDetailDto(
                            "패키지 이름",
                            "패키지 설명",
                            List.of("장거리 운전"),
                            List.of(new HMGDataDto("구매자 절반 이상이", "2384", "최근 90일 동안"))
                    ));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("잘못된 양식의 옵션 식별자로 요청할 경우 400 상태 코드를 반환해야 한다.")
        void returnStatusCode404WhenGetOptionDetailByWrongOptionId() {
            //given
            //when
            ResponseEntity<OptionDetailResponseDto> response = trimOptionController.getOptionDetail("X1", 1L);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("존재하지 않는 옵션 식별자로 요청할 경우 404 상태 코드를 반환해야 한다.")
        void returnStatusCode404WhenGetOptionDetailByNotExistOptionId() {
            //given
            //when
            ResponseEntity<OptionDetailResponseDto> response = trimOptionController.getOptionDetail("P-1", 1L);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자로 요청할 경우 404 상태 코드를 반환해야 한다.")
        void returnStatusCode404WhenGetOptionDetailByNotExistTrimId() {
            //given
            //when
            ResponseEntity<OptionDetailResponseDto> response = trimOptionController.getOptionDetail("O1", -1L);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    private static TrimOptionListResponseDto.TrimOptionDto getTrimOptionDto(String id) {
        return TrimOptionListResponseDto.TrimOptionDto.builder()
                .id(id)
                .name(id)
                .parentCategory("A")
                .childCategory("a")
                .imageUrl("image")
                .price(0)
                .chosen(0)
                .hashTags(List.of("A", "B", "C"))
                .hasHMGData(false)
                .build();
    }
}
