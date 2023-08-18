package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dao.EstimateDao;

import java.util.HashMap;


@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class EstimateCommandRepositoryImpl implements EstimateCommandRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(EstimateDao estimateDao) throws DataAccessException {
        Long nextId = jdbcTemplate.queryForObject("SELECT Max(id) FROM estimates", new HashMap<>(), Long.class) + 1;


        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", estimateDao.getDetailTrimId())
                .addValue("exteriorColorId", estimateDao.getTrimExteriorColorId())
                .addValue("exteriorColorId", estimateDao.getTrimInteriorColorId())
                .addValue("modelOptionIds", estimateDao.getModelOptionIds())
                .addValue("modelPackageIds", estimateDao.getModelPackageIds())
                .addValue("nextId", nextId);

        String SQL = "INSERT INTO estimates ( id, detail_trim_id, trim_exterior_color_id, trim_interior_color_id ) " +
                "VALUES ( :nextId, :detailTrimId, :exteriorColorId, :exteriorColorId )";

        jdbcTemplate.update(SQL, parameters);

        SqlParameterSource[] array = estimateDao.getModelPackageIds().stream()
                .map(id -> new MapSqlParameterSource()
                        .addValue("packageId", id)
                        .addValue("nextId", nextId))
                .toArray(SqlParameterSource[]::new);
        String SQL2 = "INSERT INTO estimate_packages ( estimate_id, model_package_id ) VALUES ( :nextId, :packageId ) ";
        jdbcTemplate.batchUpdate(SQL2, array);

        SqlParameterSource[] array2 = estimateDao.getModelOptionIds().stream()
                .map(id -> new MapSqlParameterSource()
                        .addValue("optionId", id)
                        .addValue("nextId", nextId))
                .toArray(SqlParameterSource[]::new);
        String SQL3 = "INSERT INTO estimate_options VALUES ( :nextId, :optionId ) ";

        jdbcTemplate.batchUpdate(SQL3, array2);
    }
}
