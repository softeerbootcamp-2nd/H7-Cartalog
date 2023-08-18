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
@DisplayName("견적서 등록 Repository 테스트")
class EstimateCommandRepositoryImplTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    EstimateQueryRepository estimateQueryRepository;
    EstimateCommandRepository estimateCommandRepository;

    @BeforeEach
    void setUp() {
        estimateQueryRepository = new EstimateQueryRepositoryImpl(jdbcTemplate);
        estimateCommandRepository = new EstimateCommandRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("save 테스트")
    class saveTest {
        @Test
        @DisplayName("생성 테스트")
        void createTest() {
            //given
            EstimateCommandRepository.EstimateSaveDto request = EstimateCommandRepository.EstimateSaveDto.builder()
                    .detailTrimId(9L)
                    .trimInteriorColorId(7L)
                    .trimExteriorColorId(7L)
                    .modelOptionIds(List.of(103L, 106L))
                    .modelPackageIds(List.of(1L, 2L, 4L))
                    .build();

            EstimateRequestDto expected = EstimateRequestDto.builder()
                    .detailTrimId(9L)
                    .exteriorColorCode("A2B")
                    .interiorColorCode("I49")
                    .selectOptionOrPackageIds(List.of("O103", "O106", "P1", "P2", "P4"))
                    .build();

            //when
            estimateCommandRepository.save(request);

            //then
            Long estimateId = estimateQueryRepository.findEstimateIdByRequestDto(expected);
            assertThat(estimateId).isNotNull();
        }
    }

}