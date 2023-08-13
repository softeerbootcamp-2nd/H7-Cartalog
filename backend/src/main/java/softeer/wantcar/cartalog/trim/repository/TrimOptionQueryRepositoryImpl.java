package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.global.ServerPath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TrimOptionQueryRepositoryImpl implements TrimOptionQueryRepository {
    private final ServerPath serverPath;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @AllArgsConstructor
    @Builder
    @Getter
    private static class TrimOptionQueryResult {
        private Long id;
        private String name;
        private String parentCategory;
        private String childCategory;
        private String imageUrl;
        private int price;
        private boolean basic;
        private boolean colorCondition;
        private Long trimInteriorColorId;
        private String hashTag;
        private Long hmgModelOptionId;
    }

    @Override
    public List<TrimOptionInfo> findPackagesByDetailTrimId(Long detailTrimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", detailTrimId);
        List<TrimOptionQueryResult> queryResults = jdbcTemplate.query(QueryString.findPackagesByTrimId,
                parameters, (rs, rowNum) -> getTrimOptionQueryResult(rs, false));
        return getTrimOptionInfos(detailTrimId, queryResults);
    }

    @Override
    public List<TrimOptionInfo> findOptionsByDetailTrimId(Long detailTrimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", detailTrimId);
        List<TrimOptionQueryResult> queryResults = jdbcTemplate.query(QueryString.findOptionsByDetailTrimId,
                parameters, (rs, rowNum) -> getTrimOptionQueryResult(rs, true));
        return getTrimOptionInfos(detailTrimId, queryResults);
    }

    private List<TrimOptionInfo> getTrimOptionInfos(Long detailTrimId, List<TrimOptionQueryResult> queryResults) {
        if(queryResults.isEmpty()) {
            return null;
        }
        Map<Long, List<TrimOptionQueryResult>> trimOptionQueryResults = queryResults.stream()
                .collect(Collectors.groupingBy(TrimOptionQueryResult::getId));

        List<TrimOptionInfo> trimOptionInfos = new ArrayList<>();
        for (Long detailTrimOptionId : trimOptionQueryResults.keySet()) {
            trimOptionInfos.add(getTrimOptionInfo(detailTrimId, trimOptionQueryResults, detailTrimOptionId));
        }
        return trimOptionInfos;
    }

    private TrimOptionInfo getTrimOptionInfo(Long detailTrimId, Map<Long, List<TrimOptionQueryResult>> trimOptionQueryResults, Long detailTrimOptionId) {
        List<TrimOptionQueryResult> optionQueryResults = trimOptionQueryResults.get(detailTrimOptionId);
        TrimOptionQueryResult optionQueryResult = optionQueryResults.get(0);
        return TrimOptionInfo.builder()
                .id(detailTrimId)
                .name(optionQueryResult.getName())
                .parentCategory(optionQueryResult.parentCategory)
                .childCategory(optionQueryResult.getChildCategory())
                .imageUrl(serverPath.attachImageServerPath(optionQueryResult.getImageUrl()))
                .price(optionQueryResult.getPrice())
                .basic(optionQueryResult.isBasic())
                .colorCondition(optionQueryResult.isColorCondition())
                .trimInteriorColorIds(getTrimInteriorColorIds(optionQueryResults))
                .hashTags(getOptionHasTags(optionQueryResults))
                .hasHMGData(isOptionHasHmgData(optionQueryResults))
                .build();
    }

    private static List<Long> getTrimInteriorColorIds(List<TrimOptionQueryResult> trimOptionInfos) {
        return trimOptionInfos.stream()
                .map(TrimOptionQueryResult::getTrimInteriorColorId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static List<String> getOptionHasTags(List<TrimOptionQueryResult> trimOptionInfos) {
        return trimOptionInfos.stream()
                .map(TrimOptionQueryResult::getHashTag)
                .collect(Collectors.toList());
    }

    private static boolean isOptionHasHmgData(List<TrimOptionQueryResult> trimOptionInfos) {
        return trimOptionInfos.stream()
                .map(TrimOptionQueryResult::getHmgModelOptionId)
                .anyMatch(Objects::nonNull);
    }

    private static TrimOptionQueryResult getTrimOptionQueryResult(ResultSet rs, boolean isOption) throws SQLException {
        TrimOptionQueryResult.TrimOptionQueryResultBuilder builder = TrimOptionQueryResult.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .parentCategory(rs.getString("parentCategory"))
                .childCategory(null)
                .imageUrl(rs.getString("imageUrl"))
                .price(rs.getInt("price"))
                .basic(false)
                .colorCondition(rs.getBoolean("colorCondition"))
                .trimInteriorColorId(rs.getLong("trimInteriorColorId"))
                .hashTag(rs.getString("hashTag"))
                .hmgModelOptionId(rs.getLong("hmgModelOptionId"));

        if(isOption) {
            builder.basic(rs.getBoolean("basic"));
            builder.childCategory(rs.getString("childCategory"));
        }

        return builder.build();
    }
}
