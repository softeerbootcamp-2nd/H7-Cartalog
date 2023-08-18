package softeer.wantcar.cartalog.estimate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EstimateServiceImplTest {
    EstimateQueryRepository estimateQueryRepository;
    EstimateCommandRepository estimateCommandRepository;
    TrimColorQueryRepository trimColorQueryRepository;
    TrimQueryRepository trimQueryRepository;
    EstimateService estimateService;

    @BeforeEach
    void setUp() {
        estimateQueryRepository = mock(EstimateQueryRepository.class);
        estimateCommandRepository = mock(EstimateCommandRepository.class);
        trimColorQueryRepository = mock(TrimColorQueryRepository.class);
        trimQueryRepository = mock(TrimQueryRepository.class);
        estimateService = new EstimateServiceImpl(estimateQueryRepository, estimateCommandRepository, trimColorQueryRepository, trimQueryRepository);
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
            when(estimateQueryRepository.findEstimateIdByRequestDto(any())).thenReturn(estimateId);

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
            when(estimateQueryRepository.findEstimateIdByRequestDto(any())).thenReturn(null).thenReturn(estimateId);

            //when
            Long result = estimateService.saveOrFindEstimateId(requestDto);

            //then
            verify(estimateCommandRepository, times(1)).save(any());
            verify(estimateQueryRepository, times(2)).findEstimateIdByRequestDto(any());
            assertThat(result).isEqualTo(estimateId);
        }

        @Test
        @DisplayName("잘못된 견적서가 없으면 IllegalException 에러를 발생해야 한다.")
        void test() {
            //given
            Long estimateId = 1L;
            EstimateRequestDto requestDto = mock(EstimateRequestDto.class);
            when(estimateQueryRepository.findEstimateIdByRequestDto(any())).thenReturn(null).thenReturn(estimateId);

            //when
            Long result = estimateService.saveOrFindEstimateId(requestDto);

            //then
            verify(estimateCommandRepository, times(1)).save(any());
            verify(estimateQueryRepository, times(2)).findEstimateIdByRequestDto(any());
            assertThat(result).isEqualTo(estimateId);
        }
    }
}