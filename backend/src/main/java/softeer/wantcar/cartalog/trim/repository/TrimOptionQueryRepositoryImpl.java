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
import java.util.*;
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
        return jdbcTemplate.queryForList(QueryString.findMultipleSelectableCategories,
                new HashMap<>(), String.class);
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
        return jdbcTemplate.queryForList(QueryString.findModelOptionIdsByPackageId, parameters, Long.class);

    }

    @Override
    public List<OptionPackageInfoDto> findOptionPackageInfoByOptionPackageIds(List<Long> optionIds, List<Long> packageIds) {
        if (optionIds.isEmpty() || packageIds.isEmpty()) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", optionIds)
                .addValue("packageIds", packageIds);

        return jdbcTemplate.query(QueryString.findOptionPackageInfoByOptionPackageIds, parameters,
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
                .imageUrl(optionQueryResult.getImageUrl())
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

    private TrimOptionQueryResult getTrimOptionQueryResult(ResultSet rs, boolean isOption) throws SQLException {
        TrimOptionQueryResult.TrimOptionQueryResultBuilder builder = TrimOptionQueryResult.builder()
                .id((isOption ? "O" : "P") + rs.getLong("id"))
                .name(rs.getString("name"))
                .parentCategory(rs.getString("parent_category"))
                .childCategory(null)
                .imageUrl(serverPath.attachImageServerPath(rs.getString("image_url")))
                .price(rs.getInt("price"))
                .basic(false)
                .colorCondition(rs.getBoolean("color_condition"))
                .trimInteriorColorCode(rs.getString("trim_interior_color_code"))
                .hashTag(rs.getString("hash_tag"))
                .hmgModelOptionId(rs.getLong("hmg_model_option_id"));

        if (isOption) {
            builder.basic(rs.getBoolean("basic"));
            builder.childCategory(rs.getString("child_category"));
        }

        return builder.build();
    }
}
