package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.DetailTrimInfoDto;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TrimQueryRepositoryImpl implements TrimQueryRepository {
    private final ServerPath serverPath;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @AllArgsConstructor
    @Builder
    @Getter
    private static class TrimListQueryResult {
        private Long trimId;
        private String trimName;
        private String description;
        private int minPrice;
        private int maxPrice;
        private String exteriorImageUrl;
        private String interiorImageUrl;
        private String wheelImageUrl;
        private String hmgName;
        private String hmgVal;
        private String hmgMeasure;
        private String hmgUnit;
        private String defaultModelTypeChildCategory;
        private Long defaultModelTypeOptionId;
        private String defaultModelTypeOptionName;
        private int defaultModelTypeOptionPrice;
        private String defaultExteriorColorCode;
        private String defaultExteriorColorName;
        private int defaultExteriorColorPrice;
        private String defaultInteriorColorCode;
        private String defaultInteriorColorName;
        private int defaultInteriorColorPrice;
    }

    @Override
    public TrimListResponseDto findTrimsByBasicModelId(Long basicModelId) {
        TrimListResponseDto.TrimListResponseDtoBuilder trimListResponseDtoBuilder = TrimListResponseDto.builder();
        SqlParameterSource parameters = new MapSqlParameterSource("basicModelId", basicModelId);

        List<TrimListQueryResult> trimListQueryResults = jdbcTemplate.query(
                QueryString.findTrimsByBasicModelId,
                parameters,
                (rs, rowNum) -> getTrimListQueryResult(rs, trimListResponseDtoBuilder));

        if (trimListQueryResults.isEmpty()) {
            return null;
        }

        Map<Long, List<TrimListQueryResult>> queryResultsGroupByTrimId = trimListQueryResults.stream()
                .collect(Collectors.groupingBy(TrimListQueryResult::getTrimId));

        for (Long trimId : queryResultsGroupByTrimId.keySet()) {
            trimListResponseDtoBuilder.trim(getTrimDto(queryResultsGroupByTrimId, trimId));
        }

        return trimListResponseDtoBuilder.build();
    }

    @Override
    public DetailTrimInfoDto findDetailTrimInfoByTrimIdAndModelTypeIds(Long trimId, List<Long> modelTypeIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("modelTypeIds", modelTypeIds)
                .addValue("modelTypeCount", modelTypeIds.size());

        try {
            return jdbcTemplate.queryForObject(
                    QueryString.findDetailTrimInfoByTrimIdAndModelTypes,
                    parameters,
                    (rs, rowNum) -> getDetailTrimInfoDto(rs));
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Long findTrimIdByDetailTrimId(Long detailTrimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", detailTrimId);

        try {
            return jdbcTemplate.queryForObject(
                    "Select trim_id FROM detail_trims WHERE id = :detailTrimId",
                    parameters,
                    Long.class);
        } catch (DataAccessException e) {
            return null;
        }
    }

    private DetailTrimInfoDto getDetailTrimInfoDto(ResultSet rs) throws SQLException {
        return DetailTrimInfoDto.builder()
                .detailTrimId(rs.getLong("detailTrimId"))
                .displacement(rs.getDouble("displacement"))
                .fuelEfficiency(rs.getDouble("fuelEfficiency"))
                .build();
    }

    private TrimListResponseDto.TrimDto getTrimDto(Map<Long, List<TrimListQueryResult>> queryResultsGroupByTrimId, Long trimId) {
        List<TrimListQueryResult> trimInfos = queryResultsGroupByTrimId.get(trimId);
        TrimListQueryResult trimInfo = trimInfos.get(0);
        TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder = TrimListResponseDto.TrimDto.builder()
                .id(trimId)
                .name(trimInfo.getTrimName())
                .description(trimInfo.getDescription())
                .maxPrice(trimInfo.getMaxPrice())
                .minPrice(trimInfo.getMinPrice())
                .exteriorImageUrl(serverPath.attachImageServerPath(trimInfo.getExteriorImageUrl()))
                .interiorImageUrl(serverPath.attachImageServerPath(trimInfo.getInteriorImageUrl()))
                .wheelImageUrl(serverPath.attachImageServerPath(trimInfo.getWheelImageUrl()));

        getTrimDefaultModelTypes(trimInfos, trimInfo, trimDtoBuilder);
        getTrimHMGData(trimInfos, trimDtoBuilder);

        return trimDtoBuilder.build();
    }

    private static void getTrimHMGData(List<TrimListQueryResult> trimInfos, TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder) {
        List<HMGDataDto> hmgInfos = trimInfos.stream()
                .filter(r -> r.getHmgVal() != null && isStringRealNumber(r))
                .sorted(Comparator.comparing(r -> Double.parseDouble(r.getHmgVal()), Comparator.reverseOrder()))
                .map(r -> new HMGDataDto(r.getHmgName(),
                        ((int) Double.parseDouble(r.getHmgVal())) + r.getHmgUnit(),
                        r.getHmgMeasure()))
                .distinct()
                .limit(3)
                .collect(Collectors.toList());

        trimDtoBuilder.hmgData(hmgInfos);
    }

    private static void getTrimDefaultModelTypes(List<TrimListQueryResult> trimInfos, TrimListQueryResult trimInfo, TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder) {
        Map<String, List<TrimListQueryResult>> defaultTrimInfos = trimInfos.stream()
                .collect(Collectors.groupingBy(TrimListQueryResult::getDefaultModelTypeChildCategory));

        List<TrimListResponseDto.ModelTypeDto> modelTypeDtoList = new ArrayList<>();
        for (String modelType : defaultTrimInfos.keySet()) {
            TrimListQueryResult modelTypeOption = defaultTrimInfos.get(modelType).get(0);
            modelTypeDtoList.add(
                    new TrimListResponseDto.ModelTypeDto(modelType, new TrimListResponseDto.OptionDto(
                            modelTypeOption.getDefaultModelTypeOptionId(),
                            modelTypeOption.getDefaultModelTypeOptionName(),
                            modelTypeOption.getDefaultModelTypeOptionPrice())));
        }

        trimDtoBuilder.defaultInfo(new TrimListResponseDto.DefaultTrimInfoDto(
                modelTypeDtoList, getDefaultExteriorColor(trimInfo), getDefaultInteriorColor(trimInfo)));
    }

    private static TrimListResponseDto.ColorDto getDefaultExteriorColor(TrimListQueryResult trimInfo) {
        return TrimListResponseDto.ColorDto.builder()
                .code(trimInfo.getDefaultExteriorColorCode())
                .name(trimInfo.getDefaultExteriorColorName())
                .price(trimInfo.getDefaultExteriorColorPrice())
                .build();
    }

    private static TrimListResponseDto.ColorDto getDefaultInteriorColor(TrimListQueryResult trimInfo) {
        return TrimListResponseDto.ColorDto.builder()
                .code(trimInfo.getDefaultInteriorColorCode())
                .name(trimInfo.getDefaultInteriorColorName())
                .price(trimInfo.getDefaultInteriorColorPrice())
                .build();
    }

    private static TrimListQueryResult getTrimListQueryResult(ResultSet rs,
                                                              TrimListResponseDto.TrimListResponseDtoBuilder builder) throws SQLException {
        builder.modelName(rs.getString("modelName"));
        return TrimListQueryResult.builder()
                .trimId(rs.getLong("trimId"))
                .trimName(rs.getString("trimName"))
                .description(rs.getString("description"))
                .minPrice(rs.getInt("minPrice"))
                .maxPrice(rs.getInt("maxPrice"))
                .exteriorImageUrl(rs.getString("exteriorImageUrl"))
                .interiorImageUrl(rs.getString("interiorImageUrl"))
                .wheelImageUrl(rs.getString("wheelImageUrl"))
                .hmgName(rs.getString("hmgName"))
                .hmgVal(rs.getString("hmgVal"))
                .hmgMeasure(rs.getString("hmgMeasure"))
                .hmgUnit(rs.getString("hmgUnit"))
                .defaultModelTypeChildCategory(rs.getString("defaultModelTypeChildCategory"))
                .defaultModelTypeOptionId(rs.getLong("defaultModelTypeOptionId"))
                .defaultModelTypeOptionName(rs.getString("defaultModelTypeOptionName"))
                .defaultModelTypeOptionPrice(rs.getInt("defaultModelTypeOptionPrice"))
                .defaultExteriorColorCode(rs.getString("defaultExteriorColorCode"))
                .defaultExteriorColorName(rs.getString("defaultExteriorColorName"))
                .defaultExteriorColorPrice(rs.getInt("defaultExteriorColorPrice"))
                .defaultInteriorColorCode(rs.getString("defaultInteriorColorCode"))
                .defaultInteriorColorName(rs.getString("defaultInteriorColorName"))
                .defaultInteriorColorPrice(rs.getInt("defaultinteriorColorPrice"))
                .build();
    }

    private static boolean isStringRealNumber(TrimListQueryResult r) {
        return r.getHmgVal().matches("[-+]?\\d*\\.?\\d+");
    }
}
