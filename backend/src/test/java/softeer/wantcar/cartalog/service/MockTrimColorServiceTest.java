package softeer.wantcar.cartalog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;
import softeer.wantcar.cartalog.trim.service.MockTrimColorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Color의 mock데이터를 반환하는 서비스 클래스 테스트")
class MockTrimColorServiceTest {

    MockTrimColorService mockColorService = new MockTrimColorService();

    @Test
    @DisplayName("언제나 같은 mock data를 반환해야 합니다.")
    void getMockData() {
        //given
        ModelExteriorColor mockData = mockColorService.getMockModelExteriorColor();

        //when
        ModelExteriorColor mockData1 = mockColorService.getMockModelExteriorColor();
        ModelExteriorColor mockData2 = mockColorService.getMockModelExteriorColor();
        ModelExteriorColor mockData3 = mockColorService.getMockModelExteriorColor();
        ModelExteriorColor mockData4 = mockColorService.getMockModelExteriorColor();
        ModelExteriorColor mockData5 = mockColorService.getMockModelExteriorColor();

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
            Long colorId = 1L;

            //when
            Map<ModelExteriorColor, Integer> exteriorColorInfos = mockColorService.findTrimExteriorColorListByTrimId(colorId);

            //then
            List<ModelExteriorColor> modelExteriorColors = new ArrayList<>(exteriorColorInfos.keySet());
            assertThat(modelExteriorColors.size()).isEqualTo(1);
            ModelExteriorColor exteriorColor = modelExteriorColors.get(0);
            assertThat(exteriorColor.getId()).isEqualTo(colorId);
        }

        @Test
        @DisplayName("잘못된 식별자로 검색했을 경우 에러를 발생해야 합니다.")
        void notFound() {
            //given
            Long id = 100L;

            //when
            //then
            assertThatThrownBy(() -> mockColorService.findTrimExteriorColorListByTrimId(id))
                    .hasMessage("존재하지 않은 트림 식별자 입니다.");
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
            Map<ModelInteriorColor, Integer> interiorColorInfos = mockColorService.findTrimInteriorColorListByTrimId(trimId, exteriorColorId);

            //then
            assertThat(interiorColorInfos.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("잘못된 식별자로 검색했을 경우 에러를 발생해야 합니다.")
        void notFound() {
            //given
            Long exteriorColorId = 100L;
            Long trimId = 100L;

            //when
            //then
            assertThatThrownBy(() -> mockColorService.findTrimInteriorColorListByTrimId(exteriorColorId, trimId))
                    .hasMessage("존재하지 않은 트림 및 외장 색상 식별자 조합 입니다.");
        }

    }

}
