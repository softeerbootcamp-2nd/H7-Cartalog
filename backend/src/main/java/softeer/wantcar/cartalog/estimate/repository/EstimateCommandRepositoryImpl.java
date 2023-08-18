package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dao.EstimateDao;

import java.util.HashMap;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Transactional
public class EstimateCommandRepositoryImpl implements EstimateCommandRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(EstimateDao estimateDao) throws DataAccessException {
        Long nextId = jdbcTemplate.queryForObject("SELECT Max(id) FROM estimates", new HashMap<>(), Long.class) + 1;

        addEstimate(estimateDao, nextId);

        addEstimateSubTable(estimateDao.getModelPackageIds(), "packageId", nextId, "INSERT INTO estimate_packages VALUES ( :nextId, :packageId ) ");
        addEstimateSubTable(estimateDao.getModelOptionIds(), "optionId", nextId, "INSERT INTO estimate_options VALUES ( :nextId, :optionId ) ");
    }

    private void addEstimate(EstimateDao estimateDao, Long nextId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", estimateDao.getDetailTrimId())
                .addValue("exteriorColorId", estimateDao.getTrimExteriorColorId())
                .addValue("exteriorColorId", estimateDao.getTrimInteriorColorId())
                .addValue("modelOptionIds", estimateDao.getModelOptionIds())
                .addValue("modelPackageIds", estimateDao.getModelPackageIds())
                .addValue("nextId", nextId);

        String addEstimateQuery = "INSERT INTO estimates ( id, detail_trim_id, trim_exterior_color_id, trim_interior_color_id ) " +
                "VALUES ( :nextId, :detailTrimId, :exteriorColorId, :exteriorColorId )";

        jdbcTemplate.update(addEstimateQuery, parameters);
    }

    private void addEstimateSubTable(List<Long> estimateDao, String valueName, Long nextId, String query) {
        SqlParameterSource[] estimatePackageQuerySources = estimateDao.stream()
                .map(id -> new MapSqlParameterSource()
                        .addValue(valueName, id)
                        .addValue("nextId", nextId))
                .toArray(SqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(query, estimatePackageQuerySources);
    }
}
