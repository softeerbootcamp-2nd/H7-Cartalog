package softeer.wantcar.cartalog.model.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.model.dto.EstimateImageDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@RequiredArgsConstructor
public class ModelQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ServerPath serverPath;

    public EstimateImageDto findCarSideExteriorAndInteriorImage(String exteriorColorCode, String interiorColorCode) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("exteriorColorCode", exteriorColorCode)
                .addValue("interiorColorCode", interiorColorCode);
        try {
            return jdbcTemplate.queryForObject(QueryString.findCarSideExteriorAndInteriorImage, parameters,
                    (rs, rowNum) -> getEstimateImageDto(rs));
        } catch (DataAccessException exception) {
            return null;
        }
    }

    private EstimateImageDto getEstimateImageDto(ResultSet rs) throws SQLException {
        return EstimateImageDto.builder()
                .sideExteriorImageUrl(serverPath.attachImageServerPath(rs.getString("sideExteriorImageUrl")))
                .interiorImageUrl(serverPath.attachImageServerPath(rs.getString("interiorImageUrl")))
                .build();
    }
}
