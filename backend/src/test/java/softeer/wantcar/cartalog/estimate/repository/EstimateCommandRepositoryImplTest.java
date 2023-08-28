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
import softeer.wantcar.cartalog.global.ServerPath;

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
        estimateQueryRepository = new EstimateQueryRepositoryImpl(jdbcTemplate, new ServerPath());
        estimateCommandRepository = new EstimateCommandRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("save 테스트")
    class saveTest {
        @Test
        @DisplayName("생성 테스트")
        void createTest() {
            //given
            EstimateDto request = EstimateDto.builder()
                    .detailTrimId(9L)
                    .trimInteriorColorId(7L)
                    .trimExteriorColorId(7L)
                    .modelOptionIds(List.of(103L, 106L))
                    .modelPackageIds(List.of(1L, 2L, 4L))
                    .build();

            //when
            estimateCommandRepository.save(request, 100);

            //then
            Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(request);
            assertThat(estimateId).isNotNull();
        }
    }

}
