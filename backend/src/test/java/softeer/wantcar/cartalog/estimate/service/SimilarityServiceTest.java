package softeer.wantcar.cartalog.estimate.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateCountResponseDto;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateCountDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateInfoDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionIdListDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionInfoDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("SameParameterValue")
@DisplayName("유사 견적 서비스 테스트")
class SimilarityServiceTest {
    SimilarityService similarityService;
    SimilarityCommandRepository similarityCommandRepository;
    SimilarityQueryRepository similarityQueryRepository;
    EstimateQueryRepository estimateQueryRepository;
    ModelOptionQueryRepository modelOptionQueryRepository;

    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        modelOptionQueryRepository = mock(ModelOptionQueryRepository.class);
        estimateQueryRepository = mock(EstimateQueryRepository.class);
        similarityQueryRepository = mock(SimilarityQueryRepository.class);
        similarityCommandRepository = mock(SimilarityCommandRepository.class);
        similarityService = new SimilarityServiceImpl(similarityQueryRepository, similarityCommandRepository,
                estimateQueryRepository, modelOptionQueryRepository);
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("getSimilarEstimateDtoList 테스트")
    class getSimilarEstimateResponseDtoListTest {
        @Test
        @DisplayName("존재하는 견적 식별자를 전달했을 때 유사 견적 목록을 반환한다")
        void returnSimilarEstimatesList() {
            //given
            when(estimateQueryRepository.findEstimateInfoBydEstimateIds(anyList()))
                    .thenReturn(List.of(getEstimateInfoDto(2L, "A")));
            when(estimateQueryRepository.findEstimateOptionsByEstimateIds(anyList()))
                    .thenReturn(List.of(
                            getEstimateOptionIfo(1L, 1L, true),
                            getEstimateOptionIfo(2L, 2L, true),
                            getEstimateOptionIfo(2L, 3L, true)));
            when(estimateQueryRepository.findEstimatePackagesByEstimateIds(anyList()))
                    .thenReturn(List.of(
                            getEstimateOptionIfo(1L, 1L, false),
                            getEstimateOptionIfo(2L, 1L, false)));

            //when
            SimilarEstimateResponseDto similarEstimateResponseDto =
                    similarityService.getSimilarEstimateInfo(1L, 2L);

            //then
            softAssertions.assertThat(similarEstimateResponseDto).isNotNull();
            softAssertions.assertThat(similarEstimateResponseDto.getTrimName()).isEqualTo("AAA");
            softAssertions.assertThat(similarEstimateResponseDto.getModelTypes()).contains("A");
            softAssertions.assertThat(similarEstimateResponseDto.getExteriorColorCode()).isEqualTo("A");
            softAssertions.assertThat(similarEstimateResponseDto.getInteriorColorCode()).isEqualTo("B");
            softAssertions.assertThat(similarEstimateResponseDto.getPrice()).isEqualTo(140);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자를 전달할 경우 null을 반환한다")
        void returnNull() {
            //given
            when(estimateQueryRepository.findEstimateInfoBydEstimateIds(anyList()))
                    .thenReturn(new ArrayList<>());
            //when
            SimilarEstimateResponseDto similarEstimateDtoList = similarityService.getSimilarEstimateInfo(1L, 2L);

            //then
            assertThat(similarEstimateDtoList).isNull();
        }
    }

    @Nested
    @DisplayName("getSimilarEstimateCounts 테스트")
    class getSimilarEstimateCounts {
        @Test
        @DisplayName("존재하는 견적 식별자를 제공할 경우 해당 견적의 개수와 유사 견적의 개수들을 반환한다")
        void returnEstimateCounts() {
            //given
            when(estimateQueryRepository.findEstimateOptionIdsByEstimateId(anyLong()))
                    .thenReturn(new EstimateOptionIdListDto(1L, List.of(1L, 2L), List.of(1L, 2L)));
            when(estimateQueryRepository.findEstimateCounts(anyList()))
                    .thenReturn(List.of(new EstimateCountDto(1L, 1L),
                            new EstimateCountDto(2L, 2L),
                            new EstimateCountDto(3L, 2L)));
            when(similarityQueryRepository.findSimilarEstimateIds(anyLong(), anyList()))
                    .thenReturn(List.of(1L, 2L, 3L));
            //when
            SimilarEstimateCountResponseDto estimateCounts =
                    similarityService.getSimilarEstimateCounts(1L);

            //then
            softAssertions.assertThat(estimateCounts.getMyEstimateCount())
                    .isEqualTo(1L);
            List<EstimateCountDto> similarEstimateCounts = estimateCounts.getSimilarEstimateCounts();
            for (EstimateCountDto similarEstimateCount : similarEstimateCounts) {
                softAssertions.assertThat(similarEstimateCount.getCount()).isEqualTo(2L);
            }
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자를 제공할 경우 null을 반환한다")
        void throwIllegalArgumentException() {
            //given
            when(estimateQueryRepository.findEstimateOptionIdsByEstimateId(anyLong()))
                    .thenReturn(null);

            //when
            //then
            assertThat(similarityService.getSimilarEstimateCounts(1L)).isNull();
        }
    }

    private static EstimateInfoDto getEstimateInfoDto(Long estimateId,
                                                      String modelTypeName) {
        return EstimateInfoDto.builder()
                .estimateId(estimateId)
                .detailTrimId(9L)
                .trimName("AAA")
                .trimPrice(100)
                .exteriorColorCode("A")
                .interiorColorCode("B")
                .modelTypeName(modelTypeName)
                .modelTypePrice(10)
                .build();
    }

    private static EstimateOptionInfoDto getEstimateOptionIfo(Long estimateId, Long optionId, boolean isOption) {
        String optionPrefix = isOption ? "O" : "P";
        return EstimateOptionInfoDto.builder()
                .estimateId(estimateId)
                .optionId(optionPrefix + optionId)
                .name(String.valueOf(optionId))
                .price(10)
                .imageUrl("imageUrl")
                .build();
    }
}
