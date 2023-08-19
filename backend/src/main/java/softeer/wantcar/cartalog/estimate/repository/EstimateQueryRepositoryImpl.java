package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionListDto;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EstimateQueryRepositoryImpl implements EstimateQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public EstimateOptionListDto findEstimateOptionIdsByEstimateId(Long estimateId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("estimateId", estimateId);
        List<EstimateOptionIdsResult> queryResult = jdbcTemplate.query(QueryString.findEstimateOptionIdsByEstimateId,
                parameters, RowMapperUtils.mapping(EstimateOptionIdsResult.class));
        if (queryResult.isEmpty()) {
            return null;
        }

        List<Long> optionIds = queryResult.stream()
                .map(EstimateOptionIdsResult::getOptionId)
                .filter(id -> id > 0)
                .distinct()
                .collect(Collectors.toList());
        List<Long> packageIds = queryResult.stream()
                .map(EstimateOptionIdsResult::getPackageId)
                .filter(id -> id > 0)
                .distinct()
                .collect(Collectors.toList());

        return new EstimateOptionListDto(queryResult.get(0).getTrimId(), optionIds, packageIds);
    }

    public long findAveragePrice(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);

        String findAveragePriceQuery = "SELECT ROUND(COALESCE(AVG(price), 0)) AS average_price " +
                "FROM ( " +
                "    SELECT COALESCE(estimates_price.price, 0) + COALESCE(package_price.price, 0) + COALESCE(option_price.price, 0) AS price " +
                "    FROM release_records " +
                "    INNER JOIN estimates " +
                "    ON estimates.id = release_records.estimate_id " +
                "    LEFT JOIN ( " +
                "        SELECT estimates.id, trims.min_price + model_exterior_colors.price + model_interior_colors.price AS price " +
                "        FROM estimates " +
                "        INNER JOIN detail_trims ON detail_trims.id = estimates.detail_trim_id " +
                "        INNER JOIN trims ON trims.id = detail_trims.trim_id " +
                "        INNER JOIN trim_exterior_colors ON trim_exterior_colors.id = estimates.trim_exterior_color_id " +
                "        INNER JOIN model_exterior_colors ON model_exterior_colors.id = trim_exterior_colors.model_exterior_color_id " +
                "        INNER JOIN trim_interior_colors ON trim_interior_colors.id = estimates.trim_interior_color_id " +
                "        INNER JOIN model_interior_colors ON model_interior_colors.code = trim_interior_colors.model_interior_color_code " +
                "    ) AS estimates_price ON estimates.id = estimates_price.id " +
                "    LEFT JOIN ( " +
                "        SELECT estimates.id, SUM(model_packages.price) AS price " +
                "        FROM estimates " +
                "        LEFT JOIN estimate_packages ON estimate_packages.estimate_id = estimates.id " +
                "        INNER JOIN model_packages ON model_packages.id = estimate_packages.model_package_id " +
                "        GROUP BY estimates.id " +
                "    ) AS package_price ON estimates.id = package_price.id " +
                "    LEFT JOIN ( " +
                "        SELECT estimates.id, SUM(model_options.price) AS price " +
                "        FROM estimates " +
                "        LEFT JOIN estimate_options ON estimate_options.estimate_id = estimates.id " +
                "        INNER JOIN model_options ON model_options.id = estimate_options.model_option_id " +
                "        GROUP BY estimates.id " +
                "    ) AS option_price ON estimates.id = option_price.id " +
                "    WHERE EXISTS ( " +
                "        SELECT 1 " +
                "        FROM detail_trims " +
                "        INNER JOIN trims ON trims.id = detail_trims.trim_id " +
                "        WHERE trims.id = :trimId AND detail_trims.id = estimates.detail_trim_id " +
                "    ) " +
                ") AS combined_price ";

        return jdbcTemplate.queryForObject(findAveragePriceQuery, parameters, long.class);
    }
}
