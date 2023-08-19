package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.HashTagMap;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimilarityQueryRepositoryImpl implements SimilarityQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<HashTagMap> findPendingHashTagMapByTrimIdAndHashTagKey(Long trimId, String hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);
        List<String> pendingHashTags = jdbcTemplate.queryForList(QueryString.findPendingHashTagKey, parameters, String.class);
        return getHashTagMaps(pendingHashTags);
    }

    @Override
    public List<String> findSimilarHashTagKeysByTrimIdAndHashTagKey(Long trimId, String hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);

        return jdbcTemplate.queryForList(QueryString.findCalculatedSimilarHashTagKeys,
                parameters, String.class);
    }

    @Override
    public List<Long> findSimilarEstimateIdsByTrimIdAndHashTagKey(Long trimId, List<String> hashTagKey) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("hashTagKey", hashTagKey);

        return jdbcTemplate.queryForList(QueryString.findSimilarEstimateIds, parameters, Long.TYPE);
    }

    @Override
    public List<String> findAllCalculatedHashTagKeys() {
        return jdbcTemplate.queryForList("SELECT DISTINCT hash_tag_key FROM similar_estimates ",
                new HashMap<>(), String.class);
    }

    private static List<HashTagMap> getHashTagMaps(List<String> pendingHashTags) {
        return pendingHashTags.stream()
                .map(HashTagMap::new)
                .collect(Collectors.toList());
    }
}
