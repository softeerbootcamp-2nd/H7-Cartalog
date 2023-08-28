package softeer.wantcar.cartalog.similarity.repository;

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
import softeer.wantcar.cartalog.similarity.repository.dto.PendingHashTagMap;
import softeer.wantcar.cartalog.similarity.repository.dto.SimilarityInfo;

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

    @BeforeEach
    void setUp() {
        similarityQueryRepository = new SimilarityQueryRepositoryImpl(jdbcTemplate);
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("existHashTagKey 테스트")
    class existHashTagKeyTest {
        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재한다면 true를 반환한다")
        void returnTrue() {
            //given
            //when
            boolean response = similarityQueryRepository.existHashTagKey(2L, "다인가족:2?레저:2?반려동물:1?스타일:2?안전:2?자녀:2?자녀 통학:1?쾌적:1");

            //then
            assertThat(response).isTrue();
        }

        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재하지 않는다면 false를 반환한다")
        void returnFalse() {
            //given
            //when
            boolean response = similarityQueryRepository.existHashTagKey(1L, "NOT-EXIST");

            //then
            assertThat(response).isFalse();
        }

        @Test
        @DisplayName("빈 문자열일 경우 true를 반환한다")
        void returnTrueWhenEmptyString() {
            //given
            //when
            boolean response = similarityQueryRepository.existHashTagKey(1L, "");

            //then
            assertThat(response).isTrue();
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
            List<PendingHashTagMap> pendingHashTagMaps = similarityQueryRepository.findPendingHashTagKeys(2L, "다인가족:2?레저:2?반려동물:1?스타일:2?안전:2?자녀:2?자녀 통학:1?쾌적:1");

            //then
            assertThat(pendingHashTagMaps.size()).isEqualTo(396);
        }

        @Test
        @DisplayName("트림 아이디와 해시 태그 키 조합이 존재하지 않는다면 빈 리스트를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<PendingHashTagMap> pendingHashTagMaps = similarityQueryRepository.findPendingHashTagKeys(0L, "NOT-EXIST");

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
            List<PendingHashTagMap> pendingHashTagMaps = similarityQueryRepository.findAllHashTagKeys(2L);

            //then
            assertThat(pendingHashTagMaps.size()).isEqualTo(396);
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
            jdbcTemplate.update("INSERT INTO hash_tag_similarities " +
                                "(target_hash_tag_index, origin_hash_tag_index, similarity) VALUES " +
                                "(2, 1, 0.3)", new MapSqlParameterSource());
            //when
            List<SimilarityInfo> similarityInfos = similarityQueryRepository.findSimilarities(2L,
                    "다인가족:2?레저:2?반려동물:1?스타일:2?안전:2?자녀:2?자녀 통학:1?쾌적:1");

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
            List<SimilarityInfo> similarityInfos = similarityQueryRepository.findSimilarities(1L, "NOT-EXISTå");

            //then
            assertThat(similarityInfos.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findSimilarEstimateIds 테스트")
    class findSimilarEstimateIdsTest {
        @BeforeEach
        void setUp() {
        }

        @Test
        @DisplayName("올바른 식별자들을 전달했을 때 유사 견적들을 최대 4개까지 List형태로 반환한다")
        void returnHashTagKeysToList() {
            System.out.println("hleoo");
            //given
            //when
            List<Long> estimateIds =
                    similarityQueryRepository.findSimilarEstimateIds(List.of(1L, 2L, 3L, 4L));

            //then
            System.out.println(estimateIds);
            softAssertions.assertThat(estimateIds).isNotNull();
            softAssertions.assertThat(estimateIds.size()).isLessThanOrEqualTo(4);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 식별자를 전달했을 때 빈 List를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<Long> notExistHashTagIndex =
                    similarityQueryRepository.findSimilarEstimateIds(List.of(-1L));

            //then
            softAssertions.assertThat(notExistHashTagIndex.isEmpty()).isTrue();
            softAssertions.assertAll();
        }
    }
}
