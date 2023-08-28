package softeer.wantcar.cartalog.estimate.service;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.dto.EstimateResponseDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionIdListDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateShareInfoDto;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.similarity.repository.SimilarityCommandRepository;
import softeer.wantcar.cartalog.similarity.repository.SimilarityQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;
import softeer.wantcar.cartalog.trim.repository.dto.OptionPackageInfoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SoftAssertionsExtension.class)
class EstimateServiceImplTest {
    @InjectSoftAssertions
    SoftAssertions softAssertions;
    EstimateQueryRepository estimateQueryRepository;
    EstimateCommandRepository estimateCommandRepository;
    TrimColorQueryRepository trimColorQueryRepository;
    TrimQueryRepository trimQueryRepository;
    SimilarityCommandRepository similarityCommandRepository;
    SimilarityQueryRepository similarityQueryRepository;
    ModelOptionQueryRepository modelOptionQueryRepository;
    TrimOptionQueryRepository trimOptionQueryRepository;
    EstimateService estimateService;

    @BeforeEach
    void setUp() {
        estimateQueryRepository = mock(EstimateQueryRepository.class);
        estimateCommandRepository = mock(EstimateCommandRepository.class);
        trimColorQueryRepository = mock(TrimColorQueryRepository.class);
        trimQueryRepository = mock(TrimQueryRepository.class);
        modelOptionQueryRepository = mock(ModelOptionQueryRepository.class);
        trimOptionQueryRepository = mock(TrimOptionQueryRepository.class);
        similarityQueryRepository = mock(SimilarityQueryRepository.class);
        similarityCommandRepository = mock(SimilarityCommandRepository.class);

        estimateService = new EstimateServiceImpl(estimateQueryRepository,
                estimateCommandRepository,
                trimColorQueryRepository,
                trimQueryRepository,
                modelOptionQueryRepository,
                similarityQueryRepository,
                similarityCommandRepository,
                trimOptionQueryRepository);
    }

    @Nested
    @DisplayName("견적서가 없으면 추가한 후 해당 견적서 id를 제공해야 한다.")
    class saveOrFindEstimateIdTest {
        @Test
        @DisplayName("이미 등록된 견적서가 있는 경우 해당 견적서의 식별자를 반환한다.")
        void successImmediatelyReturn() {
            //given
            Long estimateId = 1L;
            EstimateRequestDto requestDto = mock(EstimateRequestDto.class);
            when(estimateQueryRepository.findEstimateIdByEstimateDto(any())).thenReturn(estimateId);

            //when
            Long result = estimateService.saveOrFindEstimateId(requestDto);

            //then
            assertThat(result).isEqualTo(estimateId);
        }

        @Test
        @DisplayName("등록된 견적서가 없으면 견적서를 등록하고 해당 견적서의 식별자를 반환한다.")
        void successAfterRegister() {
            //given
            Long estimateId = 1L;
            EstimateRequestDto requestDto = mock(EstimateRequestDto.class);
            when(estimateQueryRepository.findEstimateIdByEstimateDto(any(EstimateDto.class))).thenReturn(null).thenReturn(estimateId);

            //when
            Long result = estimateService.saveOrFindEstimateId(requestDto);

            //then
            assertThat(result).isEqualTo(estimateId);
        }

        @Test
        @DisplayName("유효하지 않은 견적서를 요청하면 IllegalException 에러를 발생해야 한다.")
        void failureByWrongEstimate() {
            //given
            Long estimateId = 1L;
            EstimateRequestDto requestDto = mock(EstimateRequestDto.class);
            when(estimateQueryRepository.findEstimateIdByEstimateDto(any())).thenReturn(null).thenReturn(estimateId);

            //when
            Long result = estimateService.saveOrFindEstimateId(requestDto);

            //then
            assertThat(result).isEqualTo(estimateId);
        }
    }

    @Nested
    @DisplayName("견적서 세부 사양 조회 테스트")
    class findEstimateByEstimateIdTest {
        @Test
        @DisplayName("견적서 세부 사양에 필요한 정보를 합쳐서 반환해야 한다.")
        void success() {
            //given
            EstimateShareInfoDto shareInfoDto = mock(EstimateShareInfoDto.class);
            when(estimateQueryRepository.findEstimateShareInfoByEstimateId(anyLong())).thenReturn(shareInfoDto);
            List<Long> modelOptionIds = List.of(1L, 4L, 9L);
            when(estimateQueryRepository.findEstimateModelTypeIdsByEstimateId(anyLong())).thenReturn(modelOptionIds);
            EstimateOptionIdListDto optionListDto = mock(EstimateOptionIdListDto.class);
            when(estimateQueryRepository.findEstimateOptionIdsByEstimateId(anyLong())).thenReturn(optionListDto);

            OptionPackageInfoDto option1 = mock(OptionPackageInfoDto.class);
            OptionPackageInfoDto option4 = mock(OptionPackageInfoDto.class);
            OptionPackageInfoDto option9 = mock(OptionPackageInfoDto.class);
            List<OptionPackageInfoDto> optionPackageInfoDtoList = List.of(option1, option4, option9);
            when(trimOptionQueryRepository.findOptionPackageInfoByOptionPackageIds(modelOptionIds, new ArrayList<>())).thenReturn(optionPackageInfoDtoList);

            //when
            EstimateResponseDto estimate = estimateService.findEstimateByEstimateId(anyLong());

            //then
            softAssertions.assertThat(estimate.getTrimId()).isEqualTo(shareInfoDto.getTrimId());
            softAssertions.assertThat(estimate.getDetailTrimId()).isEqualTo(shareInfoDto.getDetailTrimId());
            softAssertions.assertThat(estimate.getExteriorColor().getColorCode()).isEqualTo(shareInfoDto.getExteriorColorCode());
            softAssertions.assertThat(estimate.getInteriorColor().getColorCode()).isEqualTo(shareInfoDto.getInteriorColorCode());
            List<String> actualSelectOptionPackageIds = estimate.getSelectOptionOrPackages().stream()
                    .map(EstimateResponseDto.OptionPackageDto::getId)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(actualSelectOptionPackageIds.containsAll(optionListDto.getAllOptionIds())).isTrue();
            softAssertions.assertThat(estimate.getExteriorCarSideImageUrl()).isEqualTo(shareInfoDto.getExteriorCarSideImageUrl());
            softAssertions.assertThat(estimate.getInteriorCarImageUrl()).isEqualTo(shareInfoDto.getInteriorColorImageUrl());
            softAssertions.assertThat(estimate.getDisplacement()).isEqualTo(shareInfoDto.getDisplacement());
            softAssertions.assertThat(estimate.getFuelEfficiency()).isEqualTo(shareInfoDto.getFuelEfficiency());
        }

        @Test
        @DisplayName("없는 견적서를 요청하면 예외를 발생해야 한다.")
        void throwIllegalException() {
            //given
            when(estimateQueryRepository.findEstimateShareInfoByEstimateId(anyLong())).thenReturn(null);

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> estimateService.findEstimateByEstimateId(anyLong());

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
