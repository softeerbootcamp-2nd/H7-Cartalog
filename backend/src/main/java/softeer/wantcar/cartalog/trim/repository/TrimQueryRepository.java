package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
public class TrimQueryRepository {
    private final JdbcTemplate jdbcTemplate;

    public TrimListResponseDto findTrimsByBasicModelId(Long basicModelId) {
        TrimListResponseDto.TrimListResponseDtoBuilder trimListResponseDtoBuilder = TrimListResponseDto.builder();

        List<TrimListQueryResult> trimListQueryResults = jdbcTemplate.query(
                QueryString.findTrimsByBasicModelIdQuery,
                new Object[]{basicModelId, basicModelId, basicModelId, basicModelId},
                new int[]{Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.BIGINT},
                (rs, rowNum) -> getTrimListQueryResult(rs, trimListResponseDtoBuilder));

        Map<Long, List<TrimListQueryResult>> queryResultsGroupByTrimId = trimListQueryResults.stream()
                .collect(Collectors.groupingBy(TrimListQueryResult::getTrimId));

        List<TrimListResponseDto.TrimDto> trimDtoList = new ArrayList<>();
        for (Long trimId : queryResultsGroupByTrimId.keySet()) {
            trimDtoList.add(getTrimDto(queryResultsGroupByTrimId, trimId));
        }

        return trimListResponseDtoBuilder.trims(trimDtoList)
                .build();
    }

    private static TrimListResponseDto.TrimDto getTrimDto(Map<Long, List<TrimListQueryResult>> queryResultsGroupByTrimId, Long trimId) {
        List<TrimListQueryResult> trimInfos = queryResultsGroupByTrimId.get(trimId);
        TrimListQueryResult trimInfo = trimInfos.get(0);
        TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder = TrimListResponseDto.TrimDto.builder()
                .id(trimId)
                .description(trimInfo.getDescription())
                .maxPrice(trimInfo.getMaxPrice())
                .minPrice(trimInfo.getMinPrice())
                .exteriorImage(trimInfo.getExteriorImage())
                .interiorImage(trimInfo.getInteriorImage());

        getTrimDefaultModelTypes(trimInfos, trimInfo, trimDtoBuilder);
        getTrimHMGData(trimInfos, trimDtoBuilder);

        return trimDtoBuilder.build();
    }

    private static void getTrimHMGData(List<TrimListQueryResult> trimInfos, TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder) {
        List<TrimListQueryResult> collect = trimInfos.stream()
                .filter(r -> r.getHmgValue() != null)
                .filter(r -> r.getHmgValue().matches("[-+]?\\d*\\.?\\d+"))
                .sorted(Comparator.comparingDouble(r -> Double.parseDouble(r.getHmgValue())))
                .limit(3)
                .collect(Collectors.toList());

        List<HMGDataDto> hmgDataDtoList = new ArrayList<>();
        for (TrimListQueryResult trimListQueryResult : collect) {
            hmgDataDtoList.add(HMGDataDto.builder()
                    .name(trimListQueryResult.getHmgName())
                    .value(trimListQueryResult.getHmgValue())
                    .measure(trimListQueryResult.getHmgMeasure())
                    .build());
        }
        trimDtoBuilder.hmgData(hmgDataDtoList);
    }

    private static void getTrimDefaultModelTypes(List<TrimListQueryResult> trimInfos, TrimListQueryResult trimInfo, TrimListResponseDto.TrimDto.TrimDtoBuilder trimDtoBuilder) {
        Map<String, List<TrimListQueryResult>> defaultTrimInfos = trimInfos.stream()
                .collect(Collectors.groupingBy(TrimListQueryResult::getDefaultModelTypeChildCategory));

        List<TrimListResponseDto.ModelTypeDto> modelTypeDtoList = new ArrayList<>();
        for (String modelType : defaultTrimInfos.keySet()) {
            TrimListQueryResult modelTypeOption = defaultTrimInfos.get(0).get(0);
            modelTypeDtoList.add(
                    new TrimListResponseDto.ModelTypeDto(modelType, new TrimListResponseDto.OptionDto(
                            modelTypeOption.getDefaultModelTypeOptionId(),
                            modelTypeOption.getDefaultModelTypeOptionName(),
                            modelTypeOption.getDefaultModelTypeOptionPrice())));
        }
        trimDtoBuilder.defaultInfo(new TrimListResponseDto.DefaultTrimInfoDto(
                modelTypeDtoList, trimInfo.getDefaultExteriorColor(), trimInfo.getDefaultInteriorColor()));
    }

    private static TrimListQueryResult getTrimListQueryResult(ResultSet rs,
                                                              TrimListResponseDto.TrimListResponseDtoBuilder builder) throws SQLException {
        builder.modelName(rs.getString("modelName"));
        return TrimListQueryResult.builder()
                .trimId(rs.getLong("trimId"))
                .description(rs.getString("description"))
                .minPrice(rs.getInt("minPrice"))
                .maxPrice(rs.getInt("maxPrice"))
                .exteriorImage(rs.getString("exteriorImage"))
                .interiorImage(rs.getString("interiorImage"))
                .hmgName(rs.getString("hmgName"))
                .hmgValue(rs.getString("hmgValue"))
                .hmgMeasure(rs.getString("hmgMeasure"))
                .defaultModelTypeChildCategory(rs.getString("defaultModelTypeChildCategory"))
                .defaultModelTypeOptionId(rs.getLong("defaultModelTypeOptionId"))
                .defaultModelTypeOptionName(rs.getString("defaultModelTypeOptionName"))
                .defaultModelTypeOptionPrice(rs.getInt("defaultModelTypeOptionPrice"))
                .defaultExteriorColor(rs.getString("defaultExteriorColor"))
                .defaultInteriorColor(rs.getString("defaultInteriorColor"))
                .build();
    }

    @AllArgsConstructor
    @Builder
    @Getter
    private static class TrimListQueryResult {
        private Long trimId;
        private String description;
        private int minPrice;
        private int maxPrice;
        private String exteriorImage;
        private String interiorImage;
        private String hmgName;
        private String hmgValue;
        private String hmgMeasure;
        private String defaultModelTypeChildCategory;
        private Long defaultModelTypeOptionId;
        private String defaultModelTypeOptionName;
        private int defaultModelTypeOptionPrice;
        private String defaultExteriorColor;
        private String defaultInteriorColor;
    }
}
