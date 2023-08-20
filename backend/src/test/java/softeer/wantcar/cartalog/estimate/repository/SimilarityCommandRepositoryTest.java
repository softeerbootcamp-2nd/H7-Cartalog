package softeer.wantcar.cartalog.estimate.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.SimilarityInfo;
import softeer.wantcar.cartalog.estimate.service.dto.PendingHashTagSimilaritySaveDto;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@DisplayName("유사 견적 관련 Command Repository 테스트")
@Transactional
@JdbcTest
@Sql({"classpath:schema.sql"})
class SimilarityCommandRepositoryTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    SimilarityCommandRepository similarityCommandRepository;
    SoftAssertions softAssertions;
    Long leblancId;

    @BeforeEach
    void setUp() {
        similarityCommandRepository = new SimilarityCommandRepositoryImpl(jdbcTemplate);
        softAssertions = new SoftAssertions();
        leblancId = jdbcTemplate.queryForObject("SELECT id FROM trims WHERE name='Le Blanc'", new HashMap<>(), Long.class);
    }
}
