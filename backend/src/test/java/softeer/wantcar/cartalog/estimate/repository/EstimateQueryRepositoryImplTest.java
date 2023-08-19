package softeer.wantcar.cartalog.estimate.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("견적서 조회 Repository 테스트")
class EstimateQueryRepositoryImplTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    EstimateQueryRepository estimateQueryRepository;

    @BeforeEach
    void setUp() {
        estimateQueryRepository = new EstimateQueryRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("트림 평균 가격 검색 테스트")
    class findAveragePriceTest {
        @Test
        @DisplayName("트림 평균 가격을 반환해야 한다.")
        void success() {
            //given
            double expect = 41395410.0000000000;

            //when
            long averagePrice = estimateQueryRepository.findAveragePrice(2L);

            //then
            assertThat(averagePrice).isEqualTo(Math.round(expect));
        }

        @Test
        @DisplayName("없는 트림 식별자를 전달하면 평균 값은 0이 되야 한다.")
        void successByNonExistenceTrimId() {
            //given
            double expect = 0;

            //when
            long averagePrice = estimateQueryRepository.findAveragePrice(-1L);

            //then
            assertThat(averagePrice).isEqualTo(Math.round(expect));
        }
    }

    @Nested
    @DisplayName("findEstimateIdByRequestDto 테스트")
    class findEstimateIdByRequestDto {
        @Test
        @DisplayName("이미 존재하는 견적서 조회 테스트")
        void findAlreadyExists() {
            //given
            EstimateDto dto = EstimateDto.builder()
                    .detailTrimId(12L)
                    .trimExteriorColorId(7L)
                    .trimInteriorColorId(7L)
                    .modelOptionIds(List.of(103L, 107L))
                    .modelPackageIds(List.of(1L, 2L, 3L, 4L))
                    .build();

            //when
            Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(dto);

            //then
            assertThat(estimateId).isEqualTo(721L);
        }

        @Test
        @DisplayName("존재하지 않는 견적서 조회시 null 반환 테스트")
        void returnNull() {
            //given
            EstimateDto dto = EstimateDto.builder()
                    .detailTrimId(12L)
                    .trimExteriorColorId(7L)
                    .trimInteriorColorId(7L)
                    .modelOptionIds(List.of(103L, 107L))
                    .modelPackageIds(List.of(1L, 2L, 3L, 4L, -1L))
                    .build();

            //when
            Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(dto);

            //then
            assertThat(estimateId).isEqualTo(null);
        }

        @Test
        @DisplayName("옵션 또는 패키지가 없을 때에도 정상적으로 견적서 조회가 가능해야 한다.")
        void findNoneData() {
            //given
            EstimateDto dto = EstimateDto.builder()
                    .detailTrimId(9L)
                    .trimExteriorColorId(7L)
                    .trimInteriorColorId(7L)
                    .modelOptionIds(List.of())
                    .modelPackageIds(List.of())
                    .build();

            //when
            Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(dto);

            //then
            assertThat(estimateId).isEqualTo(450L);
        }

        @Test
        @DisplayName("옵션이 없을 때에도 정상적으로 견적서 조회가 가능해야 한다.")
        void findUnbalancedOptionData() {
            //given
            EstimateDto dto = EstimateDto.builder()
                    .detailTrimId(9L)
                    .trimExteriorColorId(7L)
                    .trimInteriorColorId(7L)
                    .modelOptionIds(List.of())
                    .modelPackageIds(List.of(1L, 3L, 4L))
                    .build();

            //when
            Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(dto);

            //then
            assertThat(estimateId).isEqualTo(253L);
        }

        @Test
        @DisplayName("패키지가 없을 때에도 정상적으로 견적서 조회가 가능해야 한다.")
        void findUnbalancedPackageData() {
            //given
            EstimateDto dto = EstimateDto.builder()
                    .detailTrimId(9L)
                    .trimExteriorColorId(7L)
                    .trimInteriorColorId(7L)
                    .modelOptionIds(List.of(99L))
                    .modelPackageIds(List.of())
                    .build();

            //when
            Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(dto);

            //then
            assertThat(estimateId).isEqualTo(256L);
        }
    }
}
