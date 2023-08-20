package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.SimilarityInfo;
import softeer.wantcar.cartalog.estimate.service.dto.PendingHashTagSimilaritySaveDto;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@RequiredArgsConstructor
@Transactional
public class SimilarityCommandRepositoryImpl implements SimilarityCommandRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void deletePending(Long trimId, String hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);

        jdbcTemplate.update("DELETE FROM pending_hash_tag_similarities " +
                            "WHERE trim_id= :trimId AND hash_tag_key= :hashTagKey ", parameters);
    }

    @Override
    public void saveCalculatedHashTagKeys(Long trimId, String hashTagKey, List<SimilarityInfo> similarities) {
        StringBuilder insertSQL = new StringBuilder("INSERT INTO hash_tag_similarities " +
                                                    "(hash_tag_left_key, hash_tag_key, trim_id, similarity) VALUES ");
        List<String> batchInsertValues = similarities.stream()
                .map(similarityInfo -> "('" + similarityInfo.getHashTagKey() + "', '" + hashTagKey + "', " +
                                       trimId + ", " + similarityInfo.getSimilarity() + ") ")
                .collect(Collectors.toList());
        String batchInsertSQL = getBatchInsertSQL(insertSQL, batchInsertValues);
        jdbcTemplate.update(batchInsertSQL, new MapSqlParameterSource());
    }

    private String getBatchInsertSQL(StringBuilder sql, List<String> batchInsertValues) {
        for (int idx = 0; idx < batchInsertValues.size(); idx++) {
            sql.append(batchInsertValues.get(idx));
            if (idx != batchInsertValues.size() - 1) {
                sql.append(", ");
            }
        }
        return sql.toString();
    }

    public void savePendingHashTagSimilarities(PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto) {
        SqlParameterSource[] parameters = pendingHashTagSimilaritySaveDto.getPendingHashTagLeftKeys().stream()
                .map(leftKey -> new MapSqlParameterSource()
                        .addValue("pending_hash_tag_left_key", leftKey)
                        .addValue("hash_tag_key", pendingHashTagSimilaritySaveDto.getHashTagKey())
                        .addValue("trim_id", pendingHashTagSimilaritySaveDto.getTrimId()))
                .toArray(SqlParameterSource[]::new);

        jdbcTemplate.batchUpdate("INSERT INTO pending_hash_tag_similarities VALUES ( :pending_hash_tag_left_key, :hash_tag_key, :trim_id )",
                parameters);
    }
}
