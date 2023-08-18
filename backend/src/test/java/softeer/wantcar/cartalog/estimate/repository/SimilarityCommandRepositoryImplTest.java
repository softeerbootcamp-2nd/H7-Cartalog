package softeer.wantcar.cartalog.estimate.repository;

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

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("유사도 데이터 변경 Repository 테스트")
class SimilarityCommandRepositoryImplTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    SimilarityCommandRepository similarityCommandRepository;

    @BeforeEach
    void setUp() {
        similarityCommandRepository = new SimilarityCommandRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("savePendingHashTagSimilarity 테스트")
    class savePendingHashTagSimilarityTest {
        @Test
        @DisplayName("지연된 해시 테그 유사도 추가 테스트")
        void success() {
            //given
            SimilarityCommandRepository.PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto = SimilarityCommandRepository.PendingHashTagSimilaritySaveDto.builder()
                    .pendingHashTagLeftKey("C:1|D:3")
                    .hashTagKey("A:1|B:2")
                    .trimId(2L)
                    .build();

            //when
            similarityCommandRepository.savePendingHashTagSimilarity(pendingHashTagSimilaritySaveDto);

            //then
            SqlParameterSource source = new MapSqlParameterSource()
                    .addValue("hashTagKey", pendingHashTagSimilaritySaveDto.getHashTagKey())
                    .addValue("pendingHashTagLeftKey", pendingHashTagSimilaritySaveDto.getPendingHashTagLeftKey())
                    .addValue("trimId", pendingHashTagSimilaritySaveDto.getTrimId());

            String hashTagKey = jdbcTemplate.queryForObject("SELECT hash_tag_key FROM pending_hash_tag_similarities " +
                    "WHERE hash_tag_key = :hashTagKey " +
                    "AND pending_hash_tag_left_key = :pendingHashTagLeftKey " +
                    "AND trim_id = :trimId ", source, String.class);
            assertThat(hashTagKey).isEqualTo(pendingHashTagSimilaritySaveDto.getHashTagKey());
        }

    }
}