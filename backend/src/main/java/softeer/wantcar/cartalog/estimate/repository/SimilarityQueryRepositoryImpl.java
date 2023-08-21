package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.PendingHashTagMap;
import softeer.wantcar.cartalog.estimate.repository.dto.SimilarityInfo;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimilarityQueryRepositoryImpl implements SimilarityQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean existHashTagKey(Long trimId, String hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);
        try {
            jdbcTemplate.queryForObject(QueryString.findHashTagKey, parameters, Long.class);
            return true;
        } catch (DataAccessException exception) {
            return false;
        }
    }

    @Override
    public List<PendingHashTagMap> findPendingHashTagKeys(Long trimId, String hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);
        return jdbcTemplate.query(QueryString.findPendingHashTagKeys,
                parameters, (rs, rowNum) -> getPendingHashTagMap(rs));
    }

    @Override
    public List<PendingHashTagMap> findAllHashTagKeys(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);
        return jdbcTemplate.query(QueryString.findAllHashTagKeys,
                parameters, (rs, rowNum) -> getPendingHashTagMap(rs));
    }

    @Override
    public List<SimilarityInfo> findSimilarities(Long trimId, String hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);
        return jdbcTemplate.query(QueryString.findSimilarities,
                parameters, RowMapperUtils.mapping(SimilarityInfo.class));
    }

    @Override
    public List<Long> findSimilarEstimateIds(List<Long> hashTagIndexes) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("hashTagIndexes", hashTagIndexes);
        return jdbcTemplate.queryForList(QueryString.findSimilarEstimateIds, parameters, Long.TYPE);
    }

    private static PendingHashTagMap getPendingHashTagMap(ResultSet rs) throws SQLException {
        return new PendingHashTagMap(rs.getLong("idx"), rs.getString("hash_tag_key"));
    }
}
