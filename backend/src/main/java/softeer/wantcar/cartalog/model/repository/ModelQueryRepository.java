package softeer.wantcar.cartalog.model.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.entity.model.BasicModel;

import java.sql.Types;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@RequiredArgsConstructor
public class ModelQueryRepository {
    private final JdbcTemplate jdbcTemplate;

    public BasicModel findBasicModelByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, category FROM basic_models WHERE name=?",
                new Object[]{name},
                new int[]{Types.VARCHAR},
                (rs, rowNum) -> {
                    return BasicModel.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .category(rs.getString("category"))
                            .build();
                });
    }
}
