package softeer.wantcar.cartalog.model.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.entity.model.BasicModel;
import softeer.wantcar.cartalog.trim.repository.QueryString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@RequiredArgsConstructor
public class ModelQueryRepository {
    private final JdbcTemplate jdbcTemplate;

    public BasicModel findBasicModelByName(String name) {
        return jdbcTemplate.queryForObject(QueryString.findBasicModelByName,
                new Object[]{name},
                new int[]{Types.VARCHAR},
                (rs, rowNum) -> getBasicModel(rs));
    }

    private static BasicModel getBasicModel(ResultSet rs) throws SQLException {
        return BasicModel.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .category(rs.getString("category"))
                .build();
    }
}
