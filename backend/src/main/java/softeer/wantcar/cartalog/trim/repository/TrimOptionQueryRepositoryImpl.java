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
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;

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

    @Getter
    @Builder
    private static class ModelOptionDetailQueryResult {
        private String name;
        private String description;
        private String imageUrl;

        private static ModelOptionDetailQueryResult mappingModelOptionDetailQueryResult(final ResultSet rs, ServerPath serverPaths) throws SQLException {
            return ModelOptionDetailQueryResult.builder()
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .imageUrl(serverPaths.attachImageServerPath(rs.getString("image_url")))
                    .build();
        }
    }

    @Getter
    @Builder
    private static class ModelPackageDetailQueryResult {
        private String name;
        private String imageUrl;

        private static ModelPackageDetailQueryResult mappingModelPackageDetailQueryResult(final ResultSet rs, int rowNumber) throws SQLException {
            return ModelPackageDetailQueryResult.builder()
                    .name(rs.getString("name"))
                    .imageUrl(rs.getString("image_url"))
                    .build();
        }
    }

    @Getter
    @Builder
    private static class ModelPackageOptionQueryResult {
        private Long optionId;
        private String name;
        private String description;
        private String imageUrl;

        private static ModelPackageOptionQueryResult mappingModelPackageOptionQueryResult(final ResultSet rs, int rowNumber) throws SQLException {
            return ModelPackageOptionQueryResult.builder()
                    .optionId(rs.getLong("model_option_id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .imageUrl(rs.getString("image_url"))
                    .build();
        }

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
    public TrimOptionDetailResponseDto findTrimOptionDetailByDetailTrimOptionId(Long detailTrimOptionId) {
        Long optionId;
        try {
            optionId = transformModelOptionId(detailTrimOptionId);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }

        return findTrimOptionDetailResponseDto(optionId);
    }

    private TrimOptionDetailResponseDto findTrimOptionDetailResponseDto(Long optionId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionId", optionId);

        String getTrimOptionSQL = "SELECT name, description, image_url FROM model_options WHERE id = :optionId";
        ModelOptionDetailQueryResult modelOptionDetailQueryResult;
        modelOptionDetailQueryResult = jdbcTemplate.queryForObject(getTrimOptionSQL, parameters, (rs, rowNum) ->
                ModelOptionDetailQueryResult.mappingModelOptionDetailQueryResult(rs, serverPath));
        assert modelOptionDetailQueryResult != null;

        String getHashTagSQL = "SELECT hash_tag FROM model_option_hash_tags where model_option_id = :optionId";
        List<String> hashTags = jdbcTemplate.queryForList(getHashTagSQL, parameters, String.class);

        String getHMGDataSQL = "SELECT name, val, measure, unit FROM hmg_data WHERE model_option_id = :optionId";
        List<HMGDataDto> modelOptionHMGDataQueryResults = jdbcTemplate.query(getHMGDataSQL, parameters, this::mappingHMGDataDto);

        return TrimOptionDetailResponseDto.builder()
                .name(modelOptionDetailQueryResult.name)
                .description(modelOptionDetailQueryResult.description)
                .imageUrl(modelOptionDetailQueryResult.imageUrl)
                .hashTags(hashTags)
                .hmgData(modelOptionHMGDataQueryResults)
                .build();
    }

    @Override
    public TrimPackageDetailResponseDto findTrimPackageDetailByPackageId(Long packageId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("packageId", packageId);


        String getTrimPackageSQL = "SELECT name, image_url FROM detail_trim_packages WHERE id = :packageId";
        ModelPackageDetailQueryResult modelPackageDetailQueryResult = jdbcTemplate.queryForObject(getTrimPackageSQL, parameters, ModelPackageDetailQueryResult::mappingModelPackageDetailQueryResult);

        if (modelPackageDetailQueryResult == null) {
            return null;
        }

        String getHMGDataSQL = "SELECT hash_tag FROM package_hash_tags where package_id = :packageId";
        List<String> hashTags = jdbcTemplate.queryForList(getHMGDataSQL, parameters, String.class);

        String getTrimOptionsSQL = "SELECT " +
                "   model_option_id, " +
                "   name, " +
                "   description, " +
                "   image_url " +
                "FROM detail_trim_options INNER JOIN trim_package_options " +
                "   ON detail_trim_options.id = trim_package_options.detail_trim_option_id " +
                "INNER JOIN model_options " +
                "   ON detail_trim_options.model_option_id = model_options.id " +
                "WHERE  trim_package_options.trim_package_id = :packageId";

        List<ModelPackageOptionQueryResult> modelPackageOptionQueryResults = jdbcTemplate.query(getTrimOptionsSQL, parameters, ModelPackageOptionQueryResult::mappingModelPackageOptionQueryResult);

        List<TrimOptionDetailResponseDto> trimOptionDetailResponseDtoList = modelPackageOptionQueryResults.stream()
                .map(ModelPackageOptionQueryResult::getOptionId)
                .map(this::findTrimOptionDetailResponseDto)
                .collect(Collectors.toUnmodifiableList());

        return TrimPackageDetailResponseDto.builder()
                .name(modelPackageDetailQueryResult.name)
                .hashTags(hashTags)
                .imageUrl(modelPackageDetailQueryResult.imageUrl)
                .options(trimOptionDetailResponseDtoList)
                .build();
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
                .collect(Collectors.toList());
    }

    private static boolean isOptionHasHmgData(List<TrimOptionQueryResult> trimOptionInfos) {
        return trimOptionInfos.stream()
                .map(TrimOptionQueryResult::getHmgModelOptionId)
                .anyMatch(Objects::nonNull);
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

    private Long transformModelOptionId(Long detailTrimOptionId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimOptionId", detailTrimOptionId);

        String getModelOptionIdSQL = "SELECT model_options.id " +
                "FROM model_options INNER JOIN detail_trim_options " +
                "ON model_options.id = detail_trim_options.model_option_id " +
                "WHERE detail_trim_options.id = :detailTrimOptionId";

        return jdbcTemplate.queryForObject(getModelOptionIdSQL, parameters, Long.TYPE);
    }

    private HMGDataDto mappingHMGDataDto(final ResultSet rs, int rowNumber) throws SQLException {
        return HMGDataDto.builder()
                .name(rs.getString("name"))
                .value(rs.getString("val") + rs.getString("unit"))
                .measure(rs.getString("measure"))
                .build();
    }
}
