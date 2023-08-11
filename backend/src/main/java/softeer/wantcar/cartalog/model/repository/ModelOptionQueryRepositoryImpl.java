package softeer.wantcar.cartalog.model.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.global.dto.HMGDataDtoInterface;
import softeer.wantcar.cartalog.global.dto.PowerTrainHMGDataDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.dto.OptionDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
@RequiredArgsConstructor
public class ModelOptionQueryRepositoryImpl implements ModelOptionQueryRepository {
    @Value("${env.imageServerPath}")
    private String imageServerPath = "example-url";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Builder
    @AllArgsConstructor
    private static class SimpleModelOptionMapper {
        private Long modelOptionId;
        private String name;
        private String childCategory;
        private String imageUrl;
        private String description;
        private String hmgDataName;
        private String hmgDataValue;
        private String hmgDataMeasure;
        private int price;

        /**
         * @throws AssertionError            HMGData가 존재하나 value 값이 존재하지 않을 경우 발생한다.
         * @throws IndexOutOfBoundsException 엔진의 HMGData의 값이 number/number 형식이 아닐 경우 발생한다.
         */
        private HMGDataDtoInterface toHMGDataDto() {
            HMGDataDtoInterface hmgDataDto;
            if (hmgDataName == null && hmgDataValue == null && hmgDataMeasure == null) {
                return null;
            }
            if ("파워트레인/성능".equals(childCategory)) {
                assert hmgDataValue != null;
                String[] valueAndRpm = hmgDataValue.split("/", 2);
                hmgDataDto = PowerTrainHMGDataDto.builder()
                        .name(hmgDataName)
                        .value(Float.parseFloat(valueAndRpm[0]))
                        .rpm(valueAndRpm[1])
                        .measure(hmgDataMeasure)
                        .build();
            } else {
                hmgDataDto = HMGDataDto.builder()
                        .name(hmgDataName)
                        .value(hmgDataValue)
                        .measure(hmgDataMeasure)
                        .build();
            }
            return hmgDataDto;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ModelTypeListResponseDto findByModelTypeOptionsByBasicModelId(Long basicModelId) {
        String SQL = "SELECT " +
                     "  model_options.id AS model_option_id, " +
                     "  model_options.name, " +
                     "  child_category, " +
                     "  image_url, " +
                     "  description, " +
                     "  hmg_data.name AS hmg_data_name, " +
                     "  hmg_data.val AS hmg_data_value, " +
                     "  hmg_data.measure AS hmg_data_measure, " +
                     "  price_if_model_type_option AS price " +
                     "FROM model_options " +
                     "LEFT JOIN hmg_data " +
                     "ON model_options.id = hmg_data.model_option_id " +
                     "WHERE model_id = :basicModelId " +
                     "  AND price_if_model_type_option IS NOT NULL";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("basicModelId", basicModelId);

        List<SimpleModelOptionMapper> simpleModelOptionMapperList = jdbcTemplate.query(SQL, parameters,
                (rs, rowNum) -> SimpleModelOptionMapper.builder()
                        .modelOptionId(rs.getLong("model_option_Id"))
                        .name(rs.getString("name"))
                        .childCategory(rs.getString("child_category"))
                        .imageUrl(rs.getString("image_url"))
                        .description(rs.getString("description"))
                        .hmgDataName(rs.getString("hmg_data_name"))
                        .hmgDataValue(rs.getString("hmg_data_value"))
                        .hmgDataMeasure(rs.getString("hmg_data_measure"))
                        .price(rs.getInt("price"))
                        .build());

        if (simpleModelOptionMapperList.isEmpty()) {
            return null;
        }

        return buildModelTypeListResponseDto(simpleModelOptionMapperList);
    }

    @Override
    public List<String> findModelTypeCategoriesByIds(List<Long> modelTypeIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("modelTypeIds", modelTypeIds);

        List<String> categories = jdbcTemplate.query("SELECT child_category AS childCategory FROM model_options WHERE id IN (:modelTypeIds)",
                parameters, (rs, rowNum) -> rs.getString("child_category"));

        return categories.isEmpty() ? null : categories;
    }

    private ModelTypeListResponseDto buildModelTypeListResponseDto(List<SimpleModelOptionMapper> simpleModelOptionMapperList) {
        Map<String, Map<Long, OptionDto.OptionDtoBuilder>> dtoBuilderMap = new HashMap<>();
        simpleModelOptionMapperList.forEach(mapper -> {
            Map<Long, OptionDto.OptionDtoBuilder> optionDtoBuilderMap = dtoBuilderMap.getOrDefault(mapper.childCategory, new HashMap<>());
            OptionDto.OptionDtoBuilder optionDtoBuilder = optionDtoBuilderMap.getOrDefault(mapper.modelOptionId,
                    OptionDto.builder()
                            .id(mapper.modelOptionId)
                            .name(mapper.name)
                            .price(mapper.price)
                            .imageUrl(imageServerPath + "/" + mapper.imageUrl)
                            .description(mapper.description));
            HMGDataDtoInterface hmgDataDto = mapper.toHMGDataDto();
            if (hmgDataDto != null) {
                optionDtoBuilder.hmgDatum(mapper.toHMGDataDto());
            }
            optionDtoBuilderMap.put(mapper.modelOptionId, optionDtoBuilder);
            dtoBuilderMap.put(mapper.childCategory, optionDtoBuilderMap);
        });

        ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder builder = ModelTypeListResponseDto.builder();
        dtoBuilderMap.forEach((type, optionDtoBuilderMap) -> {
            ModelTypeDto.ModelTypeDtoBuilder modelTypeDtoBuilder = ModelTypeDto.builder().type(type);
            optionDtoBuilderMap.forEach((modelOptionId, optionDtoBuilder) -> modelTypeDtoBuilder.option(optionDtoBuilder.build()));
            builder.modelType(modelTypeDtoBuilder.build());
        });
        return builder.build();
    }

}
