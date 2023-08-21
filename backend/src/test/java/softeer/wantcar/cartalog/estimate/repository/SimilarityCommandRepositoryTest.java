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

    @BeforeEach
    void setUp() {
        similarityCommandRepository = new SimilarityCommandRepositoryImpl(jdbcTemplate);
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("updateLastCalculatedIndex 테스트")
    class updateLastCalculatedIndexTest {
        @Test
        @DisplayName("기존에 존재하던 해시 태그 키의 마지막 계산 인덱스를 변경한다")
        void changeLastCalculatedIndex() {
            //given
            jdbcTemplate.update("INSERT INTO pending_hash_tag_similarities " +
                                "(idx, hash_tag_key, trim_id, last_calculated_index) VALUES " +
                                "(0, 'A', 1, 1) ", new HashMap<>());

            //when
            similarityCommandRepository.updateLastCalculatedIndex(1L, "A", 2);

            //then
            Long lastCalculatedIndex = jdbcTemplate.queryForObject("SELECT last_calculated_index " +
                                                                   "FROM pending_hash_tag_similarities " +
                                                                   "WHERE idx = 0 ", new HashMap<>(), Long.class);
            assertThat(lastCalculatedIndex).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("deleteSimilarities 테스트")
    class deleteSimilaritiesTest {
        @Test
        @DisplayName("기존제 존재하던 해시 태그 키의 유사 목록을 삭제한다")
        void deleteSimilarities() {
            //given
            jdbcTemplate.update("INSERT INTO pending_hash_tag_similarities " +
                                "(idx, hash_tag_key, trim_id, last_calculated_index) VALUES " +
                                "(0, 'A', 1, 1) ", new HashMap<>());
            jdbcTemplate.update("INSERT INTO hash_tag_similarities " +
                                "(target_hash_tag_index, origin_hash_tag_index, similarity) VALUES " +
                                "(1, 0, 0.3) ", new HashMap<>());

            //when
            similarityCommandRepository.deleteSimilarities(1L, "A");

            //then
            List<Long> targetIndexes = jdbcTemplate.queryForList("SELECT target_hash_tag_index FROM hash_tag_similarities " +
                                                                 "WHERE target_hash_tag_index = 1", new HashMap<>(), Long.class);
            System.out.println(targetIndexes);
            assertThat(targetIndexes.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("saveSimilarities 테스트")
    class saveSimilaritiesTest {
        @Test
        @DisplayName("해시 태그 키의 새로운 유사 목록을 등록한다")
        void saveNewSimilarities() {
            //given
            jdbcTemplate.update("INSERT INTO pending_hash_tag_similarities " +
                                "(idx, hash_tag_key, trim_id, last_calculated_index) VALUES " +
                                "(0, 'A', 1, 1) ", new HashMap<>());
            SimilarityInfo similarityInfo = SimilarityInfo.builder()
                    .idx(1)
                    .similarity(0.2)
                    .build();
            //when
            similarityCommandRepository.saveSimilarities(1L, "A", List.of(similarityInfo));

            //then
            Long origin_hash_tag_index = jdbcTemplate.queryForObject("SELECT origin_hash_tag_index FROM hash_tag_similarities " +
                                                                  "WHERE target_hash_tag_index = 1", new HashMap<>(), Long.TYPE);
            assertThat(origin_hash_tag_index).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("saveHashTagKey 테스트")
    class saveHashTagKeyTest {
        @Test
        @DisplayName("새로운 해시 태그 키를 등록한다")
        void registerNewHashTagKey() {
            //given
            //when
            similarityCommandRepository.saveHashTagKey(1L, "A", 1);

            //then
            List<String> hashTagKeys = jdbcTemplate.queryForList("SELECT hash_tag_key FROM pending_hash_tag_similarities " +
                                                                 "WHERE trim_id = 1", new HashMap<>(), String.class);
            assertThat(hashTagKeys.size()).isEqualTo(1);
            assertThat(hashTagKeys.get(0)).isEqualTo("A");
        }
    }

    @Nested
    @DisplayName("saveSimilarEstimate 테스트")
    class saveSimilarEstimateTest {
        @Test
        @DisplayName("유사 견적을 등록한다")
        void registerSimilarEstimate() {
            //given
            jdbcTemplate.update("INSERT INTO pending_hash_tag_similarities " +
                                "(idx, hash_tag_key, trim_id, last_calculated_index) VALUES " +
                                "(0, 'A', 1, 1) ", new HashMap<>());

            //when
            similarityCommandRepository.saveSimilarEstimate(1L, "A", 1L);

            //then
            Long savedEstimateId = jdbcTemplate.queryForObject("SELECT estimate_id FROM similar_estimates " +
                                                               "WHERE hash_tag_index=0 AND estimate_id=1",
                    new MapSqlParameterSource(), Long.TYPE);
            assertThat(savedEstimateId).isEqualTo(1);
        }
    }
}
