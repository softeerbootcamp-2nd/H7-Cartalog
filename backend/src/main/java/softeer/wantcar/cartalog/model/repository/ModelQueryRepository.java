package softeer.wantcar.cartalog.model.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.entity.model.BasicModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@RequiredArgsConstructor
public class ModelQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BasicModel findBasicModelByName(String name) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", name);
        return jdbcTemplate.queryForObject("SELECT id, name, category FROM basic_models WHERE name=:name ",
                parameters, (rs, rowNum) -> getBasicModel(rs));
    }

    private static BasicModel getBasicModel(ResultSet rs) throws SQLException {
        return BasicModel.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .category(rs.getString("category"))
                .build();
    }
}
