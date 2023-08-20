package softeer.wantcar.cartalog.chosen.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChosenRepositoryImpl implements ChosenRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapperUtils.RowMapperStrategy getIdCodeRowMapperStrategy = new RowMapperUtils.RowMapperStrategy() {
        @Override
        public boolean isMappingTarget(Class<?> type, String name) {
            return Objects.equals(name, "id_code");
        }

        @Override
        public Object mapping(String name, ResultSet rs) throws SQLException {
            return rs.getObject(name);
        }
    };

    @Override
    public List<Integer> findModelTypeChosenByOptionId(List<Long> modelTypeIds, int daysAgo) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", modelTypeIds);

        String SQL = "SELECT  " +
                "    detail_model_decision_options.model_option_id AS id_code,  " +
                "    COUNT(*) AS total_records,  " +
                "    COUNT(CASE WHEN release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY THEN 1 END) AS recent_records  " +
                "FROM  release_records  " +
                "INNER JOIN estimates ON release_records.estimate_id = estimates.id  " +
                "INNER JOIN detail_trims ON detail_trims.id = estimates.detail_trim_id  " +
                "INNER JOIN detail_models ON detail_models.id = detail_trims.detail_model_id  " +
                "INNER JOIN detail_model_decision_options ON detail_model_decision_options.detail_model_id = detail_models.id  " +
                "WHERE model_option_id in ( :optionIds )  " +
                "GROUP BY detail_model_decision_options.model_option_id ";

        var query = jdbcTemplate.query(SQL, parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

        if (modelTypeIds.size() != query.size()) {
            throw new IllegalArgumentException();
        }
        
        return query.stream()
                .map(ChosenDto::getChosen)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Integer> findOptionChosenByOptionId(List<Long> optionIds, int daysAgo) {
        return null;
    }

    @Override
    public List<Integer> findPackageChosenByOptionId(List<Long> packageIds, int daysAgo) {
        return null;
    }

    @Override
    public List<Integer> findExteriorColorChosenByExteriorColorCode(List<String> exteriorColorCodes, int daysAgo) {
        return null;
    }

    @Override
    public List<Integer> findInteriorColorChosenByInteriorColorCOde(List<String> interiorColorCodes, int daysAgo) {
        return null;
    }
}
