package softeer.wantcar.cartalog.estimate.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
class EstimateQueryRepositoryImplTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    EstimateQueryRepository estimateQueryRepository;

    @BeforeEach
    void setUp() {
        estimateQueryRepository = new EstimateQueryRepositoryImpl(jdbcTemplate);
    }

    @Nested
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