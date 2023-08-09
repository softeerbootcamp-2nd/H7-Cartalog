package softeer.wantcar.cartalog.model.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.entity.HMGData;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class ModelOptionQueryRepositoryImpl implements ModelOptionQueryRepository {
    private JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Builder
    @AllArgsConstructor
    @ToString
    private static class SimpleModelOptionMapper {
        private Long model_option_Id;
        private String name;
        private String childCategory;
        private String ImageUrl;
        private String description;
        private int price;
    }

    @Override
    public ModelTypeListResponseDto findByModelId(Long id) {

        String selectSimpleModelOptionSQL = "SELECT B.model_option_Id, name, child_category, image_url, description, price FROM " +
                "(SELECT id, name, child_category, image_url, description FROM model_options WHERE id IN " +
                "(SELECT DISTINCT model_option_id FROM detail_model_decision_options WHERE detail_model_id IN " +
                "(SELECT id FROM detail_models WHERE basic_model_id = ?))) AS A " +
                "LEFT JOIN " +
                "(SELECT model_option_id, max(price) AS price FROM detail_model_decision_options GROUP BY model_option_id) AS B " +
                "ON A.id = B.model_option_ID";

        List<SimpleModelOptionMapper> simpleModelOptionMapperList = template.query(selectSimpleModelOptionSQL, (rs, rowNum) ->
                SimpleModelOptionMapper.builder()
                        .model_option_Id(rs.getLong("model_option_Id"))
                        .name(rs.getString("name"))
                        .childCategory(rs.getString("child_category"))
                        .ImageUrl(rs.getString("image_url"))
                        .description(rs.getString("description"))
                        .price(rs.getInt("price"))
                        .build(), 1);

        List<Long> modelOptionIds = new ArrayList<>();

        for (SimpleModelOptionMapper s : simpleModelOptionMapperList) {
            modelOptionIds.add(s.model_option_Id);
            log.info(s.toString());
        }

        modelOptionIds.add(14L);
        modelOptionIds.add(15L);
        modelOptionIds.add(16L);
        modelOptionIds.add(17L);

        MapSqlParameterSource inQueryParams = new MapSqlParameterSource();
        inQueryParams.addValue("ids", modelOptionIds);

        String SQL = "SELECT `value`, measure, name FROM hmg_data WHERE model_option_id IN (:ids)";
        List<HMGDataDto> hmgData = namedParameterJdbcTemplate.query(SQL, inQueryParams, (rs, rowNum) ->
                HMGDataDto.builder()
                        .value(rs.getString("value"))
                        .measure(rs.getString("measure"))
                        .name(rs.getString("name"))
                        .build());

        for (HMGDataDto hmgDatum : hmgData) {
            log.info(hmgDatum.toString());
        }


        return null;
    }
}
