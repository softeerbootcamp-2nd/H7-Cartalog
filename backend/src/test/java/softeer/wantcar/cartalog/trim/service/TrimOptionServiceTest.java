package softeer.wantcar.cartalog.trim.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("트림 옵션 서비스 테스트")
class TrimOptionServiceTest {
    TrimOptionService trimOptionService;
    TrimOptionQueryRepository trimOptionQueryRepository;
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        trimOptionQueryRepository = mock(TrimOptionQueryRepository.class);
        trimOptionService = new TrimOptionServiceImpl(trimOptionQueryRepository);
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("트림 옵션 목록 조회 테스트")
    class getTrimOptionListTest {
        @Test
        @DisplayName("존재하는 세부 트림 식별자와 내부 색상 아이디를 전달한다면 그에 맞는 옵션 목록 Dto를 반환한다")
        void returnOptionListDto() {
            //given
            when(trimOptionQueryRepository.findMultipleSelectableCategories())
                    .thenReturn(List.of("A", "B", "C"));
            when(trimOptionQueryRepository.findOptionsByDetailTrimId(1L))
                    .thenReturn(List.of(
                            getTrimOptionInfo("O1", true, 1L),
                            getTrimOptionInfo("O2", false, null)));
            when(trimOptionQueryRepository.findPackagesByDetailTrimId(1L))
                    .thenReturn(List.of(getTrimOptionInfo("P1", false, 1L)));

            //when
            TrimOptionListResponseDto responseDto = trimOptionService.getTrimOptionList(1L, 1L);

            //then
            softAssertions.assertThat(responseDto.getMultipleSelectParentCategory())
                    .containsAll(List.of("A", "B", "C"));
            softAssertions.assertThat(responseDto.getDefaultOptions().size()).isEqualTo(1);
            softAssertions.assertThat(responseDto.getSelectOptions().size()).isEqualTo(2);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("적합한 입력일 때 주어진 색상이 옵션의 색상 조건에 해당되지 않는다면 해당 옵션을 목록에서 제외한다")
        void excludeOptionByColorCondition() {
            //given
            when(trimOptionQueryRepository.findMultipleSelectableCategories())
                    .thenReturn(List.of("A", "B", "C"));
            when(trimOptionQueryRepository.findOptionsByDetailTrimId(1L))
                    .thenReturn(List.of(
                            getTrimOptionInfo("O1", true, 1L),
                            getTrimOptionInfo("O2", false, 1L)));
            when(trimOptionQueryRepository.findPackagesByDetailTrimId(1L))
                    .thenReturn(List.of(getTrimOptionInfo("P1", false, 1L)));

            //when
            TrimOptionListResponseDto responseDto = trimOptionService.getTrimOptionList(-1L, 2L);

            //then
            softAssertions.assertThat(responseDto.getDefaultOptions().isEmpty()).isTrue();
            softAssertions.assertThat(responseDto.getSelectOptions().isEmpty()).isTrue();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("세부 트림 식별자가 존재하지 않는 경우 null을 반환한다")
        void returnNull() {
            //given
            when(trimOptionQueryRepository.findMultipleSelectableCategories())
                    .thenReturn(List.of("A", "B", "C"));
            when(trimOptionQueryRepository.findOptionsByDetailTrimId(-1L))
                    .thenReturn(null);
            when(trimOptionQueryRepository.findPackagesByDetailTrimId(-1L))
                    .thenReturn(null);

            //when
            TrimOptionListResponseDto responseDto = trimOptionService.getTrimOptionList(-1L, 1L);

            //then
            assertThat(responseDto).isNull();
        }
    }

    private static TrimOptionQueryRepository.TrimOptionInfo getTrimOptionInfo(String id, boolean basic, Long trimInteriorColorId) {
        return TrimOptionQueryRepository.TrimOptionInfo.builder()
                .id(id)
                .name(id)
                .parentCategory("A")
                .childCategory("a")
                .imageUrl("image")
                .price(0)
                .basic(basic)
                .colorCondition(!Objects.isNull(trimInteriorColorId))
                .trimInteriorColorIds(Objects.isNull(trimInteriorColorId) ? new ArrayList<>() : List.of(trimInteriorColorId))
                .hashTags(new ArrayList<>())
                .hasHMGData(false)
                .build();
    }
}
