package softeer.wantcar.cartalog.trim.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.trim.dto.DetailTrimInfoDto;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("TrimQueryService 테스트")
class TrimQueryServiceTest {
    TrimQueryService trimQueryService;
    TrimQueryRepository trimQueryRepository;
    ModelOptionQueryRepository modelOptionQueryRepository;
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        trimQueryRepository = mock(TrimQueryRepositoryImpl.class);
        modelOptionQueryRepository = mock(ModelOptionQueryRepository.class);
        trimQueryService = new TrimQueryService(modelOptionQueryRepository, trimQueryRepository);
    }

    @Nested
    @DisplayName("세부 트림 정보 조회 테스트")
    class getDetailTrimInfoTest {
        @Test
        @DisplayName("적절한 트림 식별자와 모델 타입 식별자들을 전달할 경우 이에 맞는 세부 트림 정보를 반환한다.")
        void returnDetailTrimInfos() {
            //given
            when(modelOptionQueryRepository.findModelTypeCategoriesByIds(List.of(1L, 2L, 3L)))
                    .thenReturn(List.of("A", "B", "C"));
            when(trimQueryRepository.findDetailTrimInfoByTrimIdAndModelTypeIds(1L, List.of(1L, 2L, 3L)))
                    .thenReturn(DetailTrimInfoDto.builder()
                            .detailTrimId(1L)
                            .fuelEfficiency(1000)
                            .displacement(10)
                            .build());

            //when
            DetailTrimInfoDto detailTrimInfoDto = trimQueryService.getDetailTrimInfo(1L, List.of(1L, 2L, 3L));

            //then
            softAssertions.assertThat(detailTrimInfoDto.getDetailTrimId()).isEqualTo(1L);
            softAssertions.assertThat(detailTrimInfoDto.getDisplacement()).isEqualTo(1000);
            softAssertions.assertThat(detailTrimInfoDto.getFuelEfficiency()).isEqualTo(10);
        }

        @Test
        @DisplayName("존재하지 않는 식별자일 경우 null을 반환해야 한다")
        void returnNullWhenIdIsNotExist() {
            //given
            when(modelOptionQueryRepository.findModelTypeCategoriesByIds(List.of(1L, 2L, -1L)))
                    .thenReturn(null);
            when(modelOptionQueryRepository.findModelTypeCategoriesByIds(List.of(1L, 2L, 3L)))
                    .thenReturn(List.of("A", "B", "C"));
            when(trimQueryRepository.findDetailTrimInfoByTrimIdAndModelTypeIds(-1L, List.of(1L, 2L, 3L)))
                    .thenReturn(null);

            //when
            DetailTrimInfoDto wrongModelType = trimQueryService.getDetailTrimInfo(1L, List.of(1L, 2L, -1L));
            DetailTrimInfoDto wrongTrimId = trimQueryService.getDetailTrimInfo(1L, List.of(1L, 2L, -1L));

            //then
            softAssertions.assertThat(wrongModelType).isNull();
            softAssertions.assertThat(wrongTrimId).isNull();
        }

        @Test
        @DisplayName("같은 유형의 모델 타입을 전달할 경우 IllegalArgumentException 에러 발생")
        void throwIllegalArgumentException() {
            //given
            when(modelOptionQueryRepository.findModelTypeCategoriesByIds(List.of(1L, 2L, 3L)))
                    .thenReturn(List.of("A", "B", "B"));

            //when
            //then
            assertThatThrownBy(() -> trimQueryService.getDetailTrimInfo(1L, List.of(1L, 2L, 3L)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
