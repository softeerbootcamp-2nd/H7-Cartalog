package softeer.wantcar.cartalog.estimate.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateDto;
import softeer.wantcar.cartalog.estimate.repository.dto.HashTagMap;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateInfoDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionInfoDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionListDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    class getSimilarEstimateDtoListTest {
        @Test
        @DisplayName("존재하는 견적 식별자를 전달했을 때 유사 견적 목록을 반환한다")
        void returnSimilarEstimatesList() {
            //given
            mockFindEstimateInfo();
            mockLazyCalculate();
            mockFindSimilarEstimates();

            //when
            SimilarEstimateResponseDto similarEstimateResponseDto =
                    similarityService.getSimilarEstimateDtoList(1L);

            //then
            softAssertions.assertThat(similarEstimateResponseDto).isNotNull();
            List<SimilarEstimateDto> similarEstimates =
                    similarEstimateResponseDto.getSimilarEstimates();
            softAssertions.assertThat(similarEstimates.size()).isEqualTo(2);
            softAssertions.assertThat(similarEstimates.stream()
                    .map(SimilarEstimateDto::getPrice)
                    .collect(Collectors.toList())).contains(130);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자를 전달할 경우 null을 반환한다")
        void returnNull() {
            //given
            when(estimateQueryRepository.findEstimateOptionIdsByEstimateId(anyLong()))
                    .thenReturn(null);
            //when
            SimilarEstimateResponseDto similarEstimateDtoList = similarityService.getSimilarEstimateDtoList(1L);

            //then
            assertThat(similarEstimateDtoList).isNull();
        }

        private void mockFindEstimateInfo() {
            when(estimateQueryRepository.findEstimateOptionIdsByEstimateId(anyLong()))
                    .thenReturn(new EstimateOptionListDto(1L, new ArrayList<>(), new ArrayList<>()));
            when(modelOptionQueryRepository.findHashTagFromOptionsByOptionIds(anyList()))
                    .thenReturn(List.of("a"));
            when(modelOptionQueryRepository.findHashTagFromPackagesByPackageIds(anyList()))
                    .thenReturn(List.of("b"));
        }

        private void mockFindSimilarEstimates() {
            when(similarityQueryRepository.findSimilarEstimateInfoBydEstimateIds(anyList()))
                    .thenReturn(List.of(
                            getEstimateInfoDto(1L, "A"),
                            getEstimateInfoDto(2L, "A"),
                            getEstimateInfoDto(2L, "B")));
            when(similarityQueryRepository.findSimilarEstimateOptionsByEstimateIds(anyList()))
                    .thenReturn(List.of(
                            getEstimateOptionIfo(1L, 1L, true),
                            getEstimateOptionIfo(1L, 2L, true),
                            getEstimateOptionIfo(2L, 3L, true)));
            when(similarityQueryRepository.findSimilarEstimatePackagesBtEstimateIds(anyList()))
                    .thenReturn(List.of(getEstimateOptionIfo(2L, 1L, false)));
        }

        private void mockLazyCalculate() {
            when(similarityQueryRepository.findPendingHashTagMapByTrimIdAndHashTagKey(anyLong(), anyString()))
                    .thenReturn(List.of(new HashTagMap("a:1|b:1")));
            when(similarityQueryRepository.findSimilarHashTagKeysByTrimIdAndHashTagKey(anyLong(), anyString()))
                    .thenReturn(List.of("a:1|b:1", "b:1|c:1", "c:1|d:1"));
            when(similarityQueryRepository.findSimilarEstimateIdsByTrimIdAndHashTagKey(anyLong(), anyList()))
                    .thenReturn(List.of(1L, 2L, 3L));
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
