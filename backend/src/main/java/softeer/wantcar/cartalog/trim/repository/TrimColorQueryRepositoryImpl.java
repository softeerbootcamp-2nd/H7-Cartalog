package softeer.wantcar.cartalog.trim.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;
import softeer.wantcar.cartalog.trim.repository.dto.TrimExteriorColorQueryResult;
import softeer.wantcar.cartalog.trim.repository.dto.TrimInteriorColorQueryResult;

import java.util.List;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrimColorQueryRepositoryImpl implements TrimColorQueryRepository {
    private final ServerPath serverPath;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<TrimExteriorColorQueryResult> findTrimExteriorColorsByTrimId(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);

        return jdbcTemplate.query(
                QueryString.findTrimExteriorColorByTrimId, parameters,
                RowMapperUtils.mapping(TrimExteriorColorQueryResult.class, serverPath.getImageServerPathRowMapperStrategy()));
    }

    @Override
    public List<TrimInteriorColorQueryResult> findTrimInteriorColorsByTrimIdAndExteriorColorCode(Long trimId, String exteriorColorCode) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("colorCode", exteriorColorCode);

        return jdbcTemplate.query(
                QueryString.findTrimInteriorColorByTrimIdAndExteriorColorCode, parameters,
                RowMapperUtils.mapping(TrimInteriorColorQueryResult.class, serverPath.getImageServerPathRowMapperStrategy())
        );
    }

    @Override
    public Long findTrimExteriorColorIdByTrimIdAndColorCode(Long trimId, String colorCode) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("colorCode", colorCode);

        String SQL = "select trim_exterior_colors.id from trim_exterior_colors  " +
                "inner join model_exterior_colors  " +
                "on trim_exterior_colors.model_exterior_color_id = model_exterior_colors.id  " +
                "where model_exterior_colors.color_code = :colorCode " +
                "and trim_id = :trimId ";

        try {
            return jdbcTemplate.queryForObject(SQL, parameters, Long.class);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Long findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(Long trimId, String exteriorColorCode, String interiorColorCode) {
        Long trimExteriorColorId = findTrimExteriorColorIdByTrimIdAndColorCode(trimId, exteriorColorCode);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimExteriorColorId", trimExteriorColorId)
                .addValue("interiorColorCode", interiorColorCode);

        String SQL = "select id from trim_interior_colors " +
                "where model_interior_color_code = :interiorColorCode " +
                "and trim_exterior_color_id = :trimExteriorColorId ";

        try {
            return jdbcTemplate.queryForObject(SQL, parameters, Long.class);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
