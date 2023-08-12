package softeer.wantcar.cartalog.trim.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.annotation.TestMethod;

@Repository
@RequiredArgsConstructor
public class TrimTestRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @TestMethod
    @Transactional(readOnly = true)
    public Long findTrimIdByModelNameAndTrimName(String modelName, String trimName) {
        String SQL = "SELECT trims.id " +
                "FROM       basic_models " +
                "INNER JOIN trims " +
                "where      basic_models.name = :modelName " +
                "AND        trims.name = :trimName";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("modelName", modelName)
                .addValue("trimName", trimName);

        return jdbcTemplate.queryForObject(SQL, parameters, Long.class);
    }
}
