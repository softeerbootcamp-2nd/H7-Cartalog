package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;

import java.util.HashMap;
import java.util.List;


@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "DataFlowIssue"})
@Repository
@RequiredArgsConstructor
@Transactional
public class EstimateCommandRepositoryImpl implements EstimateCommandRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long save(EstimateDto estimateDto, int price) throws DataAccessException {
        Long nextId = jdbcTemplate.queryForObject("SELECT Max(id) FROM estimates", new HashMap<>(), Long.class) + 1;

        saveEstimate(estimateDto, price, nextId);
        saveEstimateOptions(estimateDto.getModelOptionIds(), nextId);
        saveEstimatePackages(estimateDto.getModelPackageIds(), nextId);
        return nextId;
    }

    private void saveEstimate(EstimateDto estimateDto, int price, Long nextId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", estimateDto.getDetailTrimId())
                .addValue("exteriorColorId", estimateDto.getTrimExteriorColorId())
                .addValue("interiorColorId", estimateDto.getTrimInteriorColorId())
                .addValue("price", price)
                .addValue("nextId", nextId);

        String addEstimateQuery = "INSERT INTO estimates ( id, detail_trim_id, trim_exterior_color_id, trim_interior_color_id, price ) " +
                                  "VALUES ( :nextId, :detailTrimId, :exteriorColorId, :interiorColorId, :price )";

        jdbcTemplate.update(addEstimateQuery, parameters);
    }

    private void saveEstimatePackages(List<Long> packageIds, Long nextId) {
        if (packageIds.isEmpty()) {
            return;
        }
        SqlParameterSource[] parameters = getBatchInsertParameters(packageIds, "packageId", nextId);
        jdbcTemplate.batchUpdate("INSERT INTO estimate_packages VALUES ( :nextId, :packageId ) ", parameters);
    }

    private void saveEstimateOptions(List<Long> optionIds, Long nextId) {
        if (optionIds.isEmpty()) {
            return;
        }
        SqlParameterSource[] parameters = getBatchInsertParameters(optionIds, "optionId", nextId);
        jdbcTemplate.batchUpdate("INSERT INTO estimate_options VALUES ( :nextId, :optionId ) ", parameters);
    }


    private SqlParameterSource[] getBatchInsertParameters(List<Long> ids, String valueName, Long nextId) {
        return ids.stream()
                .map(id -> new MapSqlParameterSource()
                        .addValue(valueName, id)
                        .addValue("nextId", nextId))
                .toArray(SqlParameterSource[]::new);
    }
}
