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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.HashTagMap;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ALL")
@DisplayName("유사 견적 관련 Repository 테스트")
@Transactional
@JdbcTest
@Sql({"classpath:schema.sql"})
class SimilarityQueryRepositoryTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    SimilarityQueryRepository similarityQueryRepository;
    SoftAssertions softAssertions;
    Long leblancId;

    @BeforeEach
    void setUp() {
        similarityQueryRepository = new SimilarityQueryRepositoryImpl(jdbcTemplate);
        softAssertions = new SoftAssertions();
        leblancId = jdbcTemplate.queryForObject("SELECT id FROM trims WHERE name='Le Blanc'", new HashMap<>(), Long.class);
    }

    @Nested
    @DisplayName("findPendingHashTagKeyByTrimIdAndHashTagKey 테스트")
    class findPendingHashTagKeyByTrimIdAndHashTagKeyTest {
        @BeforeEach
        void setUp() {
            SqlParameterSource parameters = new MapSqlParameterSource().addValue("trimId", leblancId);
            jdbcTemplate.update("INSERT INTO pending_hash_tag_similarities (hash_tag_key, pending_hash_tag_left_key, trim_id) " +
                    "VALUES " +
                    "    ('a:1|b:2', 'c:1|d:1', :trimId), " +
                    "    ('a:1|b:2', 'c:1|d:2', :trimId), " +
                    "    ('a:1|b:2', 'c:1|d:3', :trimId) ", parameters);

        }

        @Test
        @DisplayName("올바른 식별자들을 전달했을 때 계산되어야 하는 해시 태그 키들을 반환한다")
        void returnHashTagKeysToQueue() {
            //given
            //when
            List<HashTagMap> pendingHashTagKeys =
                    similarityQueryRepository.findPendingHashTagMapByTrimIdAndHashTagKey(leblancId, "a:1|b:2");

            //then
            softAssertions.assertThat(pendingHashTagKeys.size()).isEqualTo(3);
            softAssertions.assertThat(pendingHashTagKeys).containsAll(List.of(
                    new HashTagMap("c:1|d:1"),
                    new HashTagMap("c:1|d:2"),
                    new HashTagMap("c:1|d:3")));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 식별자를 전달했을 때 빈 Queue를 반환한다")
        void returnEmptyQueue() {
            //given
            //when
            List<HashTagMap> nonExistTrimIdResult =
                    similarityQueryRepository.findPendingHashTagMapByTrimIdAndHashTagKey(-1L, "a:1|b:2");
            List<HashTagMap> nonExistHashTagKeyResult =
                    similarityQueryRepository.findPendingHashTagMapByTrimIdAndHashTagKey(leblancId, "NOT-EXIST");

            //then
            softAssertions.assertThat(nonExistTrimIdResult.isEmpty()).isTrue();
            softAssertions.assertThat(nonExistHashTagKeyResult.isEmpty()).isTrue();
            softAssertions.assertAll();
        }
    }

    @Nested
    @DisplayName("findSimilarHashTagKeysByTrimIdAndHashTagKey 테스트")
    class findSimilarHashTagKeysByTrimIdAndHashTagKeyTest {
        @BeforeEach
        void setUp() {
            SqlParameterSource parameters = new MapSqlParameterSource().addValue("trimId", leblancId);
            jdbcTemplate.update("INSERT INTO hash_tag_similarities (hash_tag_key, hash_tag_left_key, trim_id, similarity) " +
                    "VALUES " +
                    "    ('a:1|b:2', 'c:1|d:1', :trimId, 0.5), " +
                    "    ('a:1|b:2', 'c:1|d:2', :trimId, 0.4), " +
                    "    ('a:1|b:2', 'c:1|d:3', :trimId, 0.7) ", parameters);
        }

        @Test
        @DisplayName("올바른 식별자들을 전달했을 때 계산된 해시 태그 정보들을 List형태로 반환한다")
        void returnHashTagKeysToList() {
            //given
            //when
            List<String> similarHashTagKeys =
                    similarityQueryRepository.findSimilarHashTagKeysByTrimIdAndHashTagKey(leblancId, "a:1|b:2");

            //then
            softAssertions.assertThat(similarHashTagKeys.size()).isEqualTo(3);
            softAssertions.assertThat(similarHashTagKeys).containsAll(List.of("c:1|d:3", "c:1|d:1", "c:1|d:2"));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 식별자를 전달했을 때 빈 List를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<String> similarHashTagKeys =
                    similarityQueryRepository.findSimilarHashTagKeysByTrimIdAndHashTagKey(leblancId, "a:1,b:2");

            //then
            assertThat(similarHashTagKeys.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findSimilarEstimateIdsByTrimIdAndHashTagKey 테스트")
    class findSimilarEstimateIdsByTrimIdAndHashTagKeyTest {
        @BeforeEach
        void setUp() {
            SqlParameterSource parameters = new MapSqlParameterSource().addValue("trimId", leblancId);
            jdbcTemplate.update("INSERT INTO similar_estimates (hash_tag_key, trim_id, estimate_id) " +
                    "VALUES " +
                    "    ('a:1|b:2', :trimId, 1), " +
                    "    ('a:1|b:2', :trimId, 2), " +
                    "    ('a:1|b:2', :trimId, 3), " +
                    "    ('a:1|b:2', :trimId, 4), " +
                    "    ('a:1|b:2', :trimId, 5) ", parameters);
        }

        @Test
        @DisplayName("올바른 식별자들을 전달했을 때 유사 견적들을 최대 4개까지 List형태로 반환한다")
        void returnHashTagKeysToList() {
            //given
            //when
            List<Long> estimateIds =
                    similarityQueryRepository.findSimilarEstimateIdsByTrimIdAndHashTagKey(leblancId, List.of("a:1|b:2"));

            //then
            softAssertions.assertThat(estimateIds).isNotNull();
            softAssertions.assertThat(estimateIds.size()).isLessThanOrEqualTo(4);
            softAssertions.assertThat(estimateIds).containsAll(List.of(1L, 2L, 3L, 4L));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 식별자를 전달했을 때 빈 List를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<Long> notExistTrimId =
                    similarityQueryRepository.findSimilarEstimateIdsByTrimIdAndHashTagKey(-1L, List.of("a:1|b:2"));
            List<Long> notExistHashTagKey =
                    similarityQueryRepository.findSimilarEstimateIdsByTrimIdAndHashTagKey(leblancId, List.of("NOT-EXIST"));

            //then
            softAssertions.assertThat(notExistTrimId.isEmpty()).isTrue();
            softAssertions.assertThat(notExistHashTagKey.isEmpty()).isTrue();
            softAssertions.assertAll();
        }
    }
}
