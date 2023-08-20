package softeer.wantcar.cartalog.estimate.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.PendingHashTagMap;
import softeer.wantcar.cartalog.estimate.repository.dto.SimilarityInfo;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    @BeforeEach
    void setUp() {
        similarityQueryRepository = new SimilarityQueryRepositoryImpl(jdbcTemplate);
        softAssertions = new SoftAssertions();
        jdbcTemplate.update("INSERT INTO pending_hash_tag_similarities " +
                            "(idx, hash_tag_key, trim_id, last_calculated_index) VALUES " +
                            "(1, 'a:1|b:1', 1, 2), " +
                            "(2, 'a:1|b:2', 1, 0), " +
                            "(3, 'a:1|b:3', 1, 0), " +
                            "(4, 'a:1|b:4', 1, 0)", new HashMap<>());

        jdbcTemplate.update("INSERT INTO hash_tag_similarities " +
                            "(origin_hash_tag_index, target_hash_tag_index, similarity) VALUES " +
                            "(1, 2, 0.3) ", new HashMap<>());
    }

    @Nested
    @DisplayName("existHashTagKey 테스트")
    class existHashTagKeyTest {
        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재한다면 true를 반환한다")
        void returnTrue() {
            //given
            //when
            boolean response = similarityQueryRepository.existHashTagKey(1L, "a:1|b:1");

            //then
            assertThat(response).isTrue();
        }

        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재하지 않는다면 false를 반환한다")
        void returnFalse() {
            //given
            //when
            boolean response = similarityQueryRepository.existHashTagKey(1L, "Z");

            //then
            assertThat(response).isFalse();
        }
    }

    @Nested
    @DisplayName("findPendingHashTagKeys 테스트")
    class findPendingHashTagKeysTest {
        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재한다면 보류된 해시 태그 키 목록을 반환한다")
        void returnPendingHashTagKeys() {
            //given
            //when
            List<PendingHashTagMap> pendingHashTagMaps = similarityQueryRepository.findPendingHashTagKeys(1L, "a:1|b:1");

            //then
            softAssertions.assertThat(pendingHashTagMaps.size()).isEqualTo(2);
            List<String> pendingHashTagKeys = pendingHashTagMaps.stream()
                    .map(PendingHashTagMap::getKey)
                    .collect(Collectors.toList());
            softAssertions.assertThat(pendingHashTagKeys).containsAll(List.of("a:1|b:3", "a:1|b:4"));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재하지 않는다면 빈 리스트를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<PendingHashTagMap> pendingHashTagMaps = similarityQueryRepository.findPendingHashTagKeys(0L, "Z");

            //then
            assertThat(pendingHashTagMaps.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findAllHashTagKeys 테스트")
    class findAllHashTagKeysTest {
        @Test
        @DisplayName("트림 식별자가 존재한다면 해당 트림 식별자를 갖는 모든 해시 태그 키 목록을 반환한다")
        void returnAllHashTagKeys() {
            //given
            //when
            List<PendingHashTagMap> pendingHashTagMaps = similarityQueryRepository.findAllHashTagKeys(1L);

            //then
            softAssertions.assertThat(pendingHashTagMaps.size()).isEqualTo(4);

            List<String> hsahTagKeys = pendingHashTagMaps.stream()
                    .map(PendingHashTagMap::getKey)
                    .collect(Collectors.toList());

            softAssertions.assertThat(hsahTagKeys).containsAll(List.of("a:1|b:1", "a:1|b:2", "a:1|b:3", "a:1|b:4"));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("트림 식별자가 존재하지 않는다면 빈 리스트를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<PendingHashTagMap> pendingHashTagMaps = similarityQueryRepository.findAllHashTagKeys(-1L);

            //then
            assertThat(pendingHashTagMaps.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findSimilarities 테스트")
    class findSimilaritiesTest {
        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재한다면 해당 조합의 유사 정보를 반환한다")
        void returnSimilarityInfos() {
            //given
            //when
            List<SimilarityInfo> similarityInfos = similarityQueryRepository.findSimilarities(1L, "a:1|b:1");

            //then
            softAssertions.assertThat(similarityInfos.size()).isEqualTo(1);
            SimilarityInfo similarityInfo = similarityInfos.get(0);
            softAssertions.assertThat(similarityInfo.getIdx()).isEqualTo(2);
            softAssertions.assertThat(similarityInfo.getSimilarity()).isEqualTo(0.3);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재하지 않는다면 빈 리스트를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<SimilarityInfo> similarityInfos = similarityQueryRepository.findSimilarities(1L, "Z");

            //then
            assertThat(similarityInfos.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findSimilarEstimateIds 테스트")
    class findSimilarEstimateIdsTest {
        @BeforeEach
        void setUp() {
            jdbcTemplate.update("INSERT INTO similar_estimates (hash_tag_key, trim_id, estimate_id) VALUES " +
                                "    ('a:1|b:1', 1, 1), " +
                                "    ('a:1|b:1', 1, 2), " +
                                "    ('a:1|b:1', 1, 3), " +
                                "    ('a:1|b:1', 1, 4), " +
                                "    ('a:1|b:1', 1, 5) ", new HashMap<>());
        }

        @Test
        @DisplayName("올바른 식별자들을 전달했을 때 유사 견적들을 최대 4개까지 List형태로 반환한다")
        void returnHashTagKeysToList() {
            //given
            //when
            List<Long> estimateIds =
                    similarityQueryRepository.findSimilarEstimateIds(1L, List.of(1L));

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
                    similarityQueryRepository.findSimilarEstimateIds(-1L, List.of(1L, 2L, 3L, 4L));
            List<Long> notExistHashTagKey =
                    similarityQueryRepository.findSimilarEstimateIds(1L, List.of(-1L));

            //then
            softAssertions.assertThat(notExistTrimId.isEmpty()).isTrue();
            softAssertions.assertThat(notExistHashTagKey.isEmpty()).isTrue();
            softAssertions.assertAll();
        }
    }
}
