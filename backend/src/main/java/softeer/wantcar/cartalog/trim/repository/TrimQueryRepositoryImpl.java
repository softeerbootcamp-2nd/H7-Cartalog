package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TrimQueryRepositoryImpl implements TrimQueryRepository {
    @Value("${env.imageServerPath}")
    private String imageServerPath = "example-url";

    private final JdbcTemplate jdbcTemplate;

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
        private String defaultInteriorColorCode;
    }

    @Override
    public TrimListResponseDto findTrimsByBasicModelId(Long basicModelId) {
        TrimListResponseDto.TrimListResponseDtoBuilder trimListResponseDtoBuilder = TrimListResponseDto.builder();

        List<TrimListQueryResult> trimListQueryResults = jdbcTemplate.query(
                QueryString.findTrimsByBasicModelIdQuery,
                new Object[]{basicModelId, basicModelId, basicModelId, basicModelId},
                new int[]{Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.BIGINT},
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

    private TrimListResponseDto.TrimDto getTrimDto(Map<Long, List<TrimListQueryResult>> queryResultsGroupByTrimId, Long trimId) {
        List<TrimListQueryResult> trimInfos = queryResultsGroupByTrimId.get(trimId);
        TrimListQueryResult trimInfo = trimInfos.get(0);
        TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder = TrimListResponseDto.TrimDto.builder()
                .id(trimId)
                .name(trimInfo.getTrimName())
                .description(trimInfo.getDescription())
                .maxPrice(trimInfo.getMaxPrice())
                .minPrice(trimInfo.getMinPrice())
                .exteriorImageUrl(imageServerPath + "/" + trimInfo.getExteriorImageUrl())
                .interiorImageUrl(imageServerPath + "/" + trimInfo.getInteriorImageUrl())
                .wheelImageUrl(imageServerPath + "/" + trimInfo.getWheelImageUrl());

        getTrimDefaultModelTypes(trimInfos, trimInfo, trimDtoBuilder);
        getTrimHMGData(trimInfos, trimDtoBuilder);

        return trimDtoBuilder.build();
    }

    private static void getTrimHMGData(List<TrimListQueryResult> trimInfos, TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder) {
        List<TrimListQueryResult> hmgInfos = trimInfos.stream()
                .filter(r -> r.getHmgVal() != null)
                .filter(r -> r.getHmgVal().matches("[-+]?\\d*\\.?\\d+"))
                .sorted(Comparator.comparingDouble(r -> Double.parseDouble(r.getHmgVal())))
                .limit(3)
                .collect(Collectors.toList());

        for (TrimListQueryResult hmgInfo : hmgInfos) {
            trimDtoBuilder.eachHMGData(HMGDataDto.builder()
                    .name(hmgInfo.getHmgName())
                    .value(hmgInfo.getHmgVal() + hmgInfo.getHmgUnit())
                    .measure(hmgInfo.getHmgMeasure())
                    .build());
        }
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
                modelTypeDtoList, trimInfo.getDefaultExteriorColorCode(), trimInfo.getDefaultInteriorColorCode()));
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
                .defaultInteriorColorCode(rs.getString("defaultInteriorColorCode"))
                .build();
    }
}
