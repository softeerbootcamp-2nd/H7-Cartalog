package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;
import softeer.wantcar.cartalog.trim.repository.dto.OptionPackageInfoDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@RequiredArgsConstructor
public class TrimOptionQueryRepositoryImpl implements TrimOptionQueryRepository {
    private final ServerPath serverPath;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @AllArgsConstructor
    @Builder
    @Getter
    private static class TrimOptionQueryResult {
        private String id;
        private String name;
        private String parentCategory;
        private String childCategory;
        private String imageUrl;
        private int price;
        private boolean basic;
        private boolean colorCondition;
        private String trimInteriorColorCode;
        private String hashTag;
        private Long hmgModelOptionId;
    }

    @Override
    public List<TrimOptionInfo> findPackagesByDetailTrimId(Long detailTrimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", detailTrimId);
        List<TrimOptionQueryResult> queryResults = jdbcTemplate.query(QueryString.findPackagesByTrimId,
                parameters, (rs, rowNum) -> getTrimOptionQueryResult(rs, false));
        return getTrimOptionInfos(queryResults);
    }

    @Override
    public List<String> findMultipleSelectableCategories() {
        return jdbcTemplate.query(QueryString.findMultipleSelectableCategories,
                (rs, rowNum) -> rs.getString("category"));
    }

    @Override
    public List<TrimOptionInfo> findOptionsByDetailTrimId(Long detailTrimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", detailTrimId);
        List<TrimOptionQueryResult> queryResults = jdbcTemplate.query(QueryString.findOptionsByDetailTrimId,
                parameters, (rs, rowNum) -> getTrimOptionQueryResult(rs, true));
        return getTrimOptionInfos(queryResults);
    }

    @Override
    @Transactional(readOnly = true)
    public ModelOptionInfo findModelOptionInfoByOptionId(Long optionId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionId", optionId);

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT name, description, image_url FROM model_options WHERE id = :optionId",
                    parameters, RowMapperUtils.mapping(ModelOptionInfo.class, List.of(serverPath.getImageServerPathRowMapperStrategy())));
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findHashTagsByOptionId(Long optionId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionId", optionId);

        return jdbcTemplate.queryForList(
                "SELECT hash_tag FROM model_option_hash_tags where model_option_id = :optionId",
                parameters, String.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HMGDataInfo> findHMGDataInfoListByOptionId(Long optionId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionId", optionId);

        return jdbcTemplate.query(
                "SELECT * FROM hmg_data WHERE model_option_id = :optionId",
                parameters, RowMapperUtils.mapping(HMGDataInfo.class));
    }

    @Override
    @Transactional(readOnly = true)
    public DetailTrimPackageInfo findDetailTrimPackageInfoByPackageId(Long packageId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("packageId", packageId);

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT name, image_url FROM model_packages WHERE id = :packageId",
                    parameters, RowMapperUtils.mapping(DetailTrimPackageInfo.class, serverPath.getImageServerPathRowMapperStrategy()));
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findPackageHashTagByPackageId(Long packageId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("packageId", packageId);

        return jdbcTemplate.queryForList(
                "SELECT hash_tag FROM model_package_hash_tags where model_package_id = :packageId",
                parameters, String.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findModelOptionIdsByPackageId(Long packageId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("packageId", packageId);

        String getTrimOptionsSQL = "SELECT " +
                                   "   model_option_id " +
                                   "FROM detail_trim_options INNER JOIN trim_package_options " +
                                   "   ON detail_trim_options.id = trim_package_options.detail_trim_option_id " +
                                   "INNER JOIN model_options " +
                                   "   ON detail_trim_options.model_option_id = model_options.id " +
                                   "WHERE  trim_package_options.trim_package_id = :packageId";

        return jdbcTemplate.queryForList(getTrimOptionsSQL, parameters, Long.class);

    }

    @Override
    public List<OptionPackageInfoDto> findOptionPackageInfoByOptionPackageIds(List<Long> optionIds, List<Long> packageIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", optionIds)
                .addValue("packageIds", packageIds);

        String getOptionInfoSQL = "SELECT id, name, child_category, price, image_url " +
                "FROM ( " +
                "    SELECT id, name, child_category, image_url, price  " +
                "    FROM model_options " +
                "    WHERE id IN ( :optionIds ) " +
                "    UNION " +
                "    SELECT id, name, NULL AS child_category, image_url, price  " +
                "    FROM model_packages " +
                "    WHERE id IN ( :packageIds ) " +
                ") AS combined_result ";

        return jdbcTemplate.query(getOptionInfoSQL, parameters,
                RowMapperUtils.mapping(OptionPackageInfoDto.class, serverPath.getImageServerPathRowMapperStrategy()));
    }

    private List<TrimOptionInfo> getTrimOptionInfos(List<TrimOptionQueryResult> queryResults) {
        if (queryResults.isEmpty()) {
            return null;
        }
        Map<String, List<TrimOptionQueryResult>> trimOptionQueryResults = queryResults.stream()
                .collect(Collectors.groupingBy(TrimOptionQueryResult::getId));

        List<TrimOptionInfo> trimOptionInfos = new ArrayList<>();
        for (String optionId : trimOptionQueryResults.keySet()) {
            trimOptionInfos.add(getTrimOptionInfo(trimOptionQueryResults, optionId));
        }
        return trimOptionInfos;
    }

    private TrimOptionInfo getTrimOptionInfo(Map<String, List<TrimOptionQueryResult>> trimOptionQueryResults, String optionId) {
        List<TrimOptionQueryResult> optionQueryResults = trimOptionQueryResults.get(optionId);
        TrimOptionQueryResult optionQueryResult = optionQueryResults.get(0);
        return TrimOptionInfo.builder()
                .id(optionId)
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

    private static List<String> getTrimInteriorColorIds(List<TrimOptionQueryResult> trimOptionInfos) {
        return trimOptionInfos.stream()
                .map(TrimOptionQueryResult::getTrimInteriorColorCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static List<String> getOptionHasTags(List<TrimOptionQueryResult> trimOptionInfos) {
        return trimOptionInfos.stream()
                .map(TrimOptionQueryResult::getHashTag)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private static boolean isOptionHasHmgData(List<TrimOptionQueryResult> trimOptionInfos) {
        return trimOptionInfos.stream()
                .map(TrimOptionQueryResult::getHmgModelOptionId)
                .anyMatch(id -> Objects.nonNull(id) && id != 0);
    }

    private static TrimOptionQueryResult getTrimOptionQueryResult(ResultSet rs, boolean isOption) throws SQLException {
        TrimOptionQueryResult.TrimOptionQueryResultBuilder builder = TrimOptionQueryResult.builder()
                .id((isOption ? "O" : "P") + rs.getLong("id"))
                .name(rs.getString("name"))
                .parentCategory(rs.getString("parentCategory"))
                .childCategory(null)
                .imageUrl(rs.getString("imageUrl"))
                .price(rs.getInt("price"))
                .basic(false)
                .colorCondition(rs.getBoolean("colorCondition"))
                .trimInteriorColorCode(rs.getString("trimInteriorColorCode"))
                .hashTag(rs.getString("hashTag"))
                .hmgModelOptionId(rs.getLong("hmgModelOptionId"));

        if (isOption) {
            builder.basic(rs.getBoolean("basic"));
            builder.childCategory(rs.getString("childCategory"));
        }

        return builder.build();
    }
}
