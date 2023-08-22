package softeer.wantcar.cartalog.trim.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;
import softeer.wantcar.cartalog.trim.repository.dto.TrimOptionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("트림 옵션 서비스 테스트")
class TrimOptionServiceTest {
    TrimOptionService trimOptionService;
    TrimOptionQueryRepository trimOptionQueryRepository;
    ChosenRepository chosenRepository;
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        trimOptionQueryRepository = mock(TrimOptionQueryRepository.class);
        chosenRepository = mock(ChosenRepository.class);
        trimOptionService = new TrimOptionServiceImpl(trimOptionQueryRepository, chosenRepository);
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
                            getTrimOptionInfo("O1", true, "AAA"),
                            getTrimOptionInfo("O2", false, null)));
            when(trimOptionQueryRepository.findPackagesByDetailTrimId(1L))
                    .thenReturn(List.of(getTrimOptionInfo("P1", false, "AAA")));

            int option2Chosen = 20;
            when(chosenRepository.findOptionChosenByOptionId(List.of("O2"))).thenReturn(List.of(new ChosenDto("O2", 20)));
            int package1Chosen = 30;
            when(chosenRepository.findPackageChosenByOptionId(List.of("P1"))).thenReturn(List.of(new ChosenDto("P1", 30)));

            //when
            TrimOptionListResponseDto responseDto = trimOptionService.getTrimOptionList(1L, "AAA");

            //then
            softAssertions.assertThat(responseDto.getMultipleSelectParentCategory())
                    .containsAll(List.of("A", "B", "C"));
            softAssertions.assertThat(responseDto.getDefaultOptions().size()).isEqualTo(1);
            softAssertions.assertThat(responseDto.getSelectOptions().size()).isEqualTo(2);
            int actualOption2Chosen = responseDto.getSelectOptions().stream()
                    .filter(trimOptionDto -> Objects.equals(trimOptionDto.getId(), "O2"))
                    .findAny()
                    .orElseThrow()
                    .getChosen();
            softAssertions.assertThat(actualOption2Chosen).isEqualTo(option2Chosen);

            int actualPackage1Chosen = responseDto.getSelectOptions().stream()
                    .filter(trimOptionDto -> Objects.equals(trimOptionDto.getId(), "P1"))
                    .findAny()
                    .orElseThrow()
                    .getChosen();
            softAssertions.assertThat(actualPackage1Chosen).isEqualTo(package1Chosen);
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
                            getTrimOptionInfo("O1", true, "BBB"),
                            getTrimOptionInfo("O2", false, "BBB")));
            when(trimOptionQueryRepository.findPackagesByDetailTrimId(1L))
                    .thenReturn(List.of(getTrimOptionInfo("P1", false, "BBB")));

            //when
            TrimOptionListResponseDto responseDto = trimOptionService.getTrimOptionList(-1L, "AAA");

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
            TrimOptionListResponseDto responseDto = trimOptionService.getTrimOptionList(-1L, "AAA");

            //then
            assertThat(responseDto).isNull();
        }
    }

    @Nested
    @DisplayName("트림 옵션 상세 조회 테스트")
    class getTrimOptionDetailTest {
        @Test
        @DisplayName("적절한 트림 옵션 식별자를 전달했을 때 옵션 상세 정보를 가진 dto를 반환해야 한다.")
        void returnTrimOptionDetailResponseDto() {
            //given
            TrimOptionQueryRepository.ModelOptionInfo modelOptionInfo = mock(TrimOptionQueryRepository.ModelOptionInfo.class);
            when(trimOptionQueryRepository.findModelOptionInfoByOptionId(anyLong())).thenReturn(modelOptionInfo);

            List<String> hashTags = List.of("해시 태그1", "해시 태그2", "해시 태그3");
            when(trimOptionQueryRepository.findHashTagsByOptionId(anyLong())).thenReturn(hashTags);

            TrimOptionQueryRepository.HMGDataInfo hmgDataInfo1 = mock(TrimOptionQueryRepository.HMGDataInfo.class);
            TrimOptionQueryRepository.HMGDataInfo hmgDataInfo2 = mock(TrimOptionQueryRepository.HMGDataInfo.class);
            List<TrimOptionQueryRepository.HMGDataInfo> hmgDataInfos = List.of(hmgDataInfo1, hmgDataInfo2);
            when(trimOptionQueryRepository.findHMGDataInfoListByOptionId(anyLong())).thenReturn(hmgDataInfos);

            //when
            TrimOptionDetailResponseDto trimOptionDetail = trimOptionService.getTrimOptionDetail(anyLong());

            //then
            assertThat(trimOptionDetail.getName()).isEqualTo(modelOptionInfo.getName());
            assertThat(trimOptionDetail.getDescription()).isEqualTo(modelOptionInfo.getDescription());
            assertThat(trimOptionDetail.getImageUrl()).isEqualTo(modelOptionInfo.getImageUrl());
            assertThat(trimOptionDetail.getHashTags()).isEqualTo(hashTags);
            assertThat(trimOptionDetail.getHmgData().size()).isEqualTo(2);
        }

        @Test
        @DisplayName("적절하지 않은 트림 옵션 식별자를 전달했을 때 null을 반환해야 한다.")
        void returnNullWhenModelOptionIdByNonexistenceDetailTrimOptionId() {
            //given
            when(trimOptionQueryRepository.findModelOptionInfoByOptionId(anyLong())).thenReturn(null);

            //when
            TrimOptionDetailResponseDto trimOptionDetail = trimOptionService.getTrimOptionDetail(anyLong());

            //then
            assertThat(trimOptionDetail).isNull();
        }

        @Test
        @DisplayName("적절한 패키지 식별자를 전달했을 때 패키지 상세 정보를 가진 dto를 반환해야 한다.")
        void returnTrimPackageDetailResponseDto() {
            //given
            TrimOptionQueryRepository.DetailTrimPackageInfo detailTrimPackageInfo = mock(TrimOptionQueryRepository.DetailTrimPackageInfo.class);
            when(trimOptionQueryRepository.findDetailTrimPackageInfoByPackageId(anyLong())).thenReturn(detailTrimPackageInfo);

            List<String> hashTags = List.of("해시 태그1", "해시 태그2", "해시 태그3");
            when(trimOptionQueryRepository.findPackageHashTagByPackageId(anyLong())).thenReturn(hashTags);

            List<Long> modelOptionIds = List.of(1L, 2L);
            when(trimOptionQueryRepository.findModelOptionIdsByPackageId(anyLong())).thenReturn(modelOptionIds);

            TrimOptionQueryRepository.ModelOptionInfo modelOptionInfo1 = mock(TrimOptionQueryRepository.ModelOptionInfo.class);
            TrimOptionQueryRepository.ModelOptionInfo modelOptionInfo2 = mock(TrimOptionQueryRepository.ModelOptionInfo.class);
            when(trimOptionQueryRepository.findModelOptionInfoByOptionId(1L)).thenReturn(modelOptionInfo1);
            when(trimOptionQueryRepository.findModelOptionInfoByOptionId(2L)).thenReturn(modelOptionInfo2);

            List<String> optionHashTags1 = List.of("해시 태그1", "해시 태그2", "해시 태그3");
            List<String> optionHashTags2 = List.of("해시 태그1", "해시 태그2", "해시 태그3");
            when(trimOptionQueryRepository.findHashTagsByOptionId(1L)).thenReturn(optionHashTags1);
            when(trimOptionQueryRepository.findHashTagsByOptionId(2L)).thenReturn(optionHashTags2);

            TrimOptionQueryRepository.HMGDataInfo hmgDataInfo1 = mock(TrimOptionQueryRepository.HMGDataInfo.class);
            TrimOptionQueryRepository.HMGDataInfo hmgDataInfo2 = mock(TrimOptionQueryRepository.HMGDataInfo.class);
            List<TrimOptionQueryRepository.HMGDataInfo> hmgDataInfos1 = List.of(hmgDataInfo1, hmgDataInfo2);
            List<TrimOptionQueryRepository.HMGDataInfo> hmgDataInfos2 = List.of();
            when(trimOptionQueryRepository.findHMGDataInfoListByOptionId(1L)).thenReturn(hmgDataInfos1);
            when(trimOptionQueryRepository.findHMGDataInfoListByOptionId(2L)).thenReturn(hmgDataInfos2);

            //when
            TrimPackageDetailResponseDto trimPackageDetail = trimOptionService.getTrimPackageDetail(anyLong());

            //then
            assertThat(trimPackageDetail.getName()).isEqualTo(detailTrimPackageInfo.getName());
            assertThat(trimPackageDetail.getImageUrl()).isEqualTo(detailTrimPackageInfo.getImageUrl());
            assertThat(trimPackageDetail.getHashTags()).isEqualTo(hashTags);
            assertThat(trimPackageDetail.getOptions().size()).isEqualTo(2);
        }

        @Test
        @DisplayName("적절하지 않은 패키지 식별자를 전달했을 때 null을 반환해야 한다.")
        void returnNullWhenTrimOptionDetailResponseDtoByNonexistenceTrimPackageId() {
            //given
            when(trimOptionQueryRepository.findDetailTrimPackageInfoByPackageId(anyLong())).thenReturn(null);

            //when
            TrimPackageDetailResponseDto trimPackageDetail = trimOptionService.getTrimPackageDetail(anyLong());

            //then
            assertThat(trimPackageDetail).isNull();
        }
    }

    private static TrimOptionInfo getTrimOptionInfo(String id, boolean basic, String trimInteriorColorId) {
        return TrimOptionInfo.builder()
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
