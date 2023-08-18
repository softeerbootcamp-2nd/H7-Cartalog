package softeer.wantcar.cartalog.estimate.repository;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("견적서 조회 Repository 테스트")
@ExtendWith(SoftAssertionsExtension.class)
class EstimateQueryRepositoryImplTest {
    @InjectSoftAssertions
    SoftAssertions softAssertions;
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

    @DisplayName("findEstimateIdByRequestDto 테스트")
    class findEstimateIdByRequestDto {
        @Test
        @DisplayName("이미 존재하는 견적서 조회 테스트")
        void findAlreadyExists() {
            //given
            EstimateRequestDto dto = EstimateRequestDto.builder()
                    .detailTrimId(12L)
                    .exteriorColorCode("A2B")
                    .interiorColorCode("I49")
                    .selectOptionOrPackageIds(List.of("P1", "P2", "P3", "P4", "O103", "O107"))
                    .build();

            //when
            Long estimateId = estimateQueryRepository.findEstimateIdByRequestDto(dto);

            //then
            assertThat(estimateId).isEqualTo(721L);
        }

        @Test
        @DisplayName("존재하지 않는 견적서 조회시 null 반환 테스트")
        void returnNull() {
            //given
            EstimateRequestDto dto = EstimateRequestDto.builder()
                    .detailTrimId(12L)
                    .exteriorColorCode("A2B")
                    .interiorColorCode("I49")
                    .selectOptionOrPackageIds(List.of("P1", "P2", "P3", "P4", "O103", "O107", "O12345"))
                    .build();

            //when
            Long estimateId = estimateQueryRepository.findEstimateIdByRequestDto(dto);

            //then
            assertThat(estimateId).isEqualTo(null);
        }
    }
}
