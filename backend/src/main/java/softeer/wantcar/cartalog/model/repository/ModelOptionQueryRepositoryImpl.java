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
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class ModelOptionQueryRepositoryImpl implements ModelOptionQueryRepository {
    private JdbcTemplate template;

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
            HMGDataDtoInterface dto;
            if("파워트레인/성능".equals(childCategory)) {
                String[] valueAndRpm = hmgDataValue.split("/", 2);
                dto = PowerTrainHMGDataDto.builder()
                        .name(hmgDataName)
                        .value(Float.parseFloat(valueAndRpm[0]))
                        .rpm(valueAndRpm[1])
                        .measure(hmgDataMeasure)
                        .build();
            } else {
                dto = HMGDataDto.builder()
                        .name(hmgDataName)
                        .value(hmgDataValue)
                        .measure(hmgDataMeasure)
                        .build();
            }
            return dto;
        }
    }

    @Override
    @Transactional
    public ModelTypeListResponseDto findByModelId(Long id) {
        String selectSimpleModelOptionSQL = "SELECT model_options.id AS model_option_id, " +
                "       model_options.name, " +
                "       child_category, " +
                "       image_url, " +
                "       description, " +
                "       hmg_data.name    AS hmg_data_name, " +
                "       hmg_data.`value` AS hmg_data_value, " +
                "       hmg_data.measure AS hmg_data_measure, " +
                "       price " +
                "FROM   model_options " +
                "       LEFT JOIN hmg_data " +
                "              ON model_options.id = hmg_data.model_option_id " +
                "       INNER JOIN (SELECT model_option_id, " +
                "                          Max(price) AS price " +
                "                   FROM   detail_model_decision_options " +
                "                   WHERE  detail_model_id IN (SELECT id " +
                "                                              FROM   detail_models " +
                "                                              WHERE  basic_model_id = ?) " +
                "                   GROUP  BY model_option_id) AS " +
                "                  MAX_PRICE_IN_TARGET_MODEL_TYPE_OPION " +
                "               ON model_options.id = MAX_PRICE_IN_TARGET_MODEL_TYPE_OPION.model_option_id; ";

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
                        .build(), 1);

        for (SimpleModelOptionMapper mapper : simpleModelOptionMapperList) {
            HMGDataDtoInterface hmgDataDto = mapper.toHMGDataDto();
            log.info(mapper.toString());
        }
        return null;
    }
}
