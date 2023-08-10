package softeer.wantcar.cartalog.model.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
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

@Repository
@AllArgsConstructor
@Slf4j
public class ModelOptionQueryRepositoryImpl implements ModelOptionQueryRepository {
    private final JdbcTemplate template;
    private static final int CHOSEN_MOCK = 38;

    @Builder
    @AllArgsConstructor
    @ToString
    private static class SimpleModelOptionMapper {
        private Long model_option_Id;
        private String name;
        private String childCategory;
        private String imageUrl;
        private String description;
        private String hmgDataName;
        private String hmgDataValue;
        private String hmgDataMeasure;
        private int price;

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
    @Transactional
    public ModelTypeListResponseDto findByModelId(Long modelId) {
        String selectSimpleModelOptionSQL = "SELECT model_options.id AS model_option_id, " +
                "       model_options.name, " +
                "       child_category, " +
                "       image_url, " +
                "       description, " +
                "       hmg_data.name              AS hmg_data_name, " +
                "       hmg_data.`value`           AS hmg_data_value, " +
                "       hmg_data.measure           AS hmg_data_measure, " +
                "       price_if_model_type_option AS price " +
                "FROM   model_options " +
                "       LEFT JOIN hmg_data " +
                "              ON model_options.id = hmg_data.model_option_id " +
                "WHERE  model_id = ? " +
                "       AND price_if_model_type_option IS NOT NULL";

        List<SimpleModelOptionMapper> simpleModelOptionMapperList = template.query(selectSimpleModelOptionSQL, (rs, rowNum) ->
                SimpleModelOptionMapper.builder()
                        .model_option_Id(rs.getLong("model_option_Id"))
                        .name(rs.getString("name"))
                        .childCategory(rs.getString("child_category"))
                        .imageUrl(rs.getString("image_url"))
                        .description(rs.getString("description"))
                        .hmgDataName(rs.getString("hmg_data_name"))
                        .hmgDataValue(rs.getString("hmg_data_value"))
                        .hmgDataMeasure(rs.getString("hmg_data_measure"))
                        .price(rs.getInt("price"))
                        .build(), modelId);

        Map<String, Map<Long, OptionDto.OptionDtoBuilder>> dtoBuilderMap = new HashMap<>();
        for (SimpleModelOptionMapper mapper : simpleModelOptionMapperList) {
            Map<Long, OptionDto.OptionDtoBuilder> optionDtoBuilderMap = dtoBuilderMap.getOrDefault(mapper.childCategory, new HashMap<>());
            OptionDto.OptionDtoBuilder optionDtoBuilder = optionDtoBuilderMap.getOrDefault(mapper.model_option_Id,
                    OptionDto.builder()
                            .id(mapper.model_option_Id)
                            .name(mapper.name)
                            .price(mapper.price)
                            .chosen(CHOSEN_MOCK)
                            .imageUrl(mapper.imageUrl)
                            .description(mapper.description));

            HMGDataDtoInterface hmgDataDto = mapper.toHMGDataDto();
            if (hmgDataDto != null) {
                optionDtoBuilder.hmgDatum(mapper.toHMGDataDto());
            }
            optionDtoBuilderMap.put(mapper.model_option_Id, optionDtoBuilder);
            dtoBuilderMap.put(mapper.childCategory, optionDtoBuilderMap);
        }

        ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder builder = ModelTypeListResponseDto.builder();
        dtoBuilderMap.forEach((type, optionDtoBuilderMap) -> {
            ModelTypeDto.ModelTypeDtoBuilder modelTypeDtoBuilder = ModelTypeDto.builder().type(type);
            optionDtoBuilderMap.forEach((modelOptionId, optionDtoBuilder) -> modelTypeDtoBuilder.option(optionDtoBuilder.build()));
            builder.modelType(modelTypeDtoBuilder.build());
        });

        return builder.build();
    }
}
