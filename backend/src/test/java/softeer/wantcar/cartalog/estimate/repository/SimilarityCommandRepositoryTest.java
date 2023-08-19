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
import softeer.wantcar.cartalog.estimate.service.dto.PendingHashTagSimilaritySaveDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Nested
    @DisplayName("savePendingHashTagSimilarities 테스트")
    class savePendingToOtherTest {
        @Test
        @DisplayName("다른 해시 태그 키들에 현재 키를 추가한다")
        void addCurrentKeyToOtherHashTagKeys() {
            //given
            //when
            PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto = PendingHashTagSimilaritySaveDto.builder()
                    .trimId(leblancId)
                    .hashTagKey("a:1|b:1")
                    .pendingHashTagLeftKeys(List.of("b:1|c:1", "b:2|c:2", "b:3|c:3"))
                    .build();
            similarityCommandRepository.savePendingHashTagSimilarities(pendingHashTagSimilaritySaveDto);

            //then
            List<String> hashTagKeys = jdbcTemplate.queryForList(
                    "SELECT pending_hash_tag_left_key FROM pending_hash_tag_similarities WHERE hash_tag_key = 'a:1|b:1' ",
                    new HashMap<>(), String.class);
            softAssertions.assertThat(hashTagKeys.size()).isEqualTo(3);
            softAssertions.assertThat(hashTagKeys).containsAll(
                    List.of("b:1|c:1", "b:2|c:2", "b:3|c:3"));
            softAssertions.assertAll();
        }
    }

    @Nested
    @DisplayName("deletePending 테스트")
    class deletePendingTest {
        @Test
        @DisplayName("보류되었던 해시 태그 키(similarities)들을 key의 보류 목록에서 삭제한다")
        void deletePendingList() {
            //given
            jdbcTemplate.update("INSERT INTO pending_hash_tag_similarities " +
                            "(pending_hash_tag_left_key, hash_tag_key, trim_id) VALUES " +
                            "('b:1|c:1', 'a:1|b:1', :trimId), " +
                            "('b:2|c:2', 'a:1|b:1', :trimId), " +
                            "('b:3|c:3', 'a:1|b:1', :trimId), " +
                            "('b:4|c:4', 'a:1|b:1', :trimId), " +
                            "('b:5|c:5', 'a:1|b:1', :trimId), " +
                            "('b:6|c:6', 'a:2|b:2', :trimId) ",
                    new MapSqlParameterSource().addValue("trimId", leblancId));

            //when
            similarityCommandRepository.deletePending(leblancId, "a:1|b:1");

            //then
            List<String> hashTagKeys = jdbcTemplate.queryForList(
                    "SELECT pending_hash_tag_left_key FROM pending_hash_tag_similarities ",
                    new HashMap<>(), String.class);
            assertThat(hashTagKeys).isNotNull();
            assertThat(hashTagKeys.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("saveCalculatedHashTagKeys 테스트")
    class saveCalculatedHashTagKeysTest {
        @Test
        @DisplayName("계산된 해시 태그 키(similarities)들을 계산된 목록에 추가한다")
        void addSimilaritiesToCalculatedList() {
            //given
            Map<String, Double> similarities = new HashMap<>();
            similarities.put("b:1|c:1", 0.5);
            similarities.put("b:2|c:2", 0.1);
            similarities.put("b:3|c:3", 0.9);
            similarities.put("b:4|c:4", 0.7);

            //when
            similarityCommandRepository.saveCalculatedHashTagKeys(leblancId, "a:1|b:1", similarities);

            //then
            List<String> hashTagKeys = jdbcTemplate.queryForList("SELECT hash_tag_left_key FROM hash_tag_similarities ",
                    new HashMap<>(), String.class);
            softAssertions.assertThat(hashTagKeys).isNotNull();
            softAssertions.assertThat(hashTagKeys.size()).isEqualTo(4);
            softAssertions.assertAll();
        }
    }
}
