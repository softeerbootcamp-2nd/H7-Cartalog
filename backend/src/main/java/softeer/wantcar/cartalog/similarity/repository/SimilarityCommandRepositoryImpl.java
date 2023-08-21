package softeer.wantcar.cartalog.similarity.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.similarity.repository.dto.SimilarityInfo;

import java.util.List;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class SimilarityCommandRepositoryImpl implements SimilarityCommandRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void updateLastCalculatedIndex(Long trimId, String hashTagKey, long lastCalculatedIndex) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey)
                .addValue("lastCalculatedIndex", lastCalculatedIndex);
        jdbcTemplate.update(QueryString.updateLastCalculatedIndex, parameters);
    }

    @Override
    public void deleteSimilarities(Long trimId, String hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);
        jdbcTemplate.update(QueryString.deleteSimilarities, parameters);
    }

    @Override
    public void saveSimilarities(Long trimId, String hashTagKey, List<SimilarityInfo> similarityInfos) {
        if(similarityInfos.isEmpty()) {
            return;
        }
        SqlParameterSource[] parameters = similarityInfos.stream()
                .map(similarityInfo -> new MapSqlParameterSource()
                        .addValue("trimId", trimId)
                        .addValue("hashTagKey", hashTagKey)
                        .addValue("targetHashTagIndex", similarityInfo.getIdx())
                        .addValue("similarity", similarityInfo.getSimilarity()))
                .toArray(SqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(QueryString.saveSimilarities, parameters);
    }

    @Override
    public void saveHashTagKey(Long trimId, String hashTagKey, long lastCalculatedIndex) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey)
                .addValue("lastCalculatedIndex", lastCalculatedIndex);
        jdbcTemplate.update(QueryString.saveHashTagKey, parameters);
    }

    @Override
    public void saveSimilarEstimate(Long trimId, String hashTagKey, Long estimateId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey)
                .addValue("estimateId", estimateId);
        jdbcTemplate.update(QueryString.saveSimilarEstimate, parameters);
    }
}
