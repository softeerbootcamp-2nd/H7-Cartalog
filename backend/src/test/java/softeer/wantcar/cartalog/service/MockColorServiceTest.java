package softeer.wantcar.cartalog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;
import softeer.wantcar.cartalog.entity.color.TrimInteriorColor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Color의 mock데이터를 반환하는 서비스 클래스 테스트")
class MockColorServiceTest {

    MockColorService mockColorService = new MockColorService();

    @Test
    @DisplayName("언제나 같은 mock data를 반환해야 합니다.")
    void getMockData() {
        //given
        TrimExteriorColor mockData = mockColorService.getMockData();

        //when
        TrimExteriorColor mockData1 = mockColorService.getMockData();
        TrimExteriorColor mockData2 = mockColorService.getMockData();
        TrimExteriorColor mockData3 = mockColorService.getMockData();
        TrimExteriorColor mockData4 = mockColorService.getMockData();
        TrimExteriorColor mockData5 = mockColorService.getMockData();

        //then
        assertThat(mockData).isEqualTo(mockData1);
        assertThat(mockData).isEqualTo(mockData2);
        assertThat(mockData).isEqualTo(mockData3);
        assertThat(mockData).isEqualTo(mockData4);
        assertThat(mockData).isEqualTo(mockData5);
    }

    @Nested
    @DisplayName("외장 mock Data 반환 테스트")
    class findTrimExteriorColorListByTrimId {

        @Test
        @DisplayName("식별자 1로 검색했을 경우 mockData 하나를 반환해야 합니다.")
        void success() {
            //given
            Long id = 1L;

            //when
            List<TrimExteriorColor> trimExteriorColorList = mockColorService.findTrimExteriorColorListByTrimId(id);

            //then
            assertThat(trimExteriorColorList.size()).isEqualTo(1);
            TrimExteriorColor color = trimExteriorColorList.get(0);
            assertThat(color.getId()).isEqualTo(id);
        }

        @Test
        @DisplayName("잘못된 식별자로 검색했을 경우 에러를 발생해야 합니다.")
        void notFound() {
            //given
            Long id = 100L;

            //when
            RuntimeException runtimeException = assertThrows(
                    RuntimeException.class,
                    () -> mockColorService.findTrimExteriorColorListByTrimId(id)
            );

            //then
            assertThat(runtimeException.getMessage()).isEqualTo("존재하지 않은 트림 식별자 입니다.");
        }

    }

    @Nested
    @DisplayName("내장 mock Data 반환 테스트")
    class findTrimInteriorColorListByTrimId {

        @Test
        @DisplayName("트림, 외장 색상 식별자 (1,1) 로 검색했을 경우 mockData 하나를 반환해야 합니다.")
        void success() {
            //given
            Long exteriorColorId = 1L;
            Long trimId = 1L;

            //when
            List<TrimInteriorColor> trimInteriorColorList = mockColorService.findTrimInteriorColorListByTrimId(trimId, exteriorColorId);

            //then
            assertThat(trimInteriorColorList.size()).isEqualTo(1);
            TrimInteriorColor color = trimInteriorColorList.get(0);
            assertThat(color.getId()).isEqualTo(trimId);
        }

        @Test
        @DisplayName("잘못된 식별자로 검색했을 경우 에러를 발생해야 합니다.")
        void notFound() {
            //given
            Long exteriorColorId = 100L;
            Long trimId = 100L;

            //when
            RuntimeException runtimeException = assertThrows(
                    RuntimeException.class,
                    () -> mockColorService.findTrimInteriorColorListByTrimId(exteriorColorId, exteriorColorId)
            );

            //then
            assertThat(runtimeException.getMessage()).isEqualTo("존재하지 않은 트림 및 외장 색상 식별자 조합 입니다.");
        }

    }

}