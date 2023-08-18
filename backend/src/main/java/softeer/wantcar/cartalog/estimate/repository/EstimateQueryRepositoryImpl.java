package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionListDto;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @Override
    public Long findAveragePrice(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);
        return jdbcTemplate.queryForObject(QueryString.findAveragePrice, parameters, Long.class);
    }

    public Long findEstimateIdByRequestDto(EstimateRequestDto estimateRequestDto) {
        List<Long> selectPackages = estimateRequestDto.getSelectOptionOrPackageIds().stream()
                .filter(id -> id.charAt(0) == 'P')
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toUnmodifiableList());

        List<Long> selectOptions = estimateRequestDto.getSelectOptionOrPackageIds().stream()
                .filter(id -> id.charAt(0) == 'O')
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toUnmodifiableList());

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", estimateRequestDto.getDetailTrimId())
                .addValue("exteriorColorCode", estimateRequestDto.getExteriorColorCode())
                .addValue("interiorColorCode", estimateRequestDto.getInteriorColorCode())
                .addValue("selectPackageIds", selectPackages)
                .addValue("selectOptionIds", selectOptions)
                .addValue("countOfPackages", selectPackages.size())
                .addValue("countOfOptions", selectOptions.size())
                .addValue("countOfSumPackages", selectPackages.size() * Math.max(selectOptions.size(), 1))
                .addValue("countOfSumOptions", Math.max(selectPackages.size(), 1) * selectOptions.size());

        String SQL = "SELECT estimates.id " +
                "FROM   estimates " +
                "       INNER JOIN trim_exterior_colors " +
                "               ON estimates.trim_exterior_color_id = trim_exterior_colors.id " +
                "       INNER JOIN model_exterior_colors " +
                "               ON trim_exterior_colors.model_exterior_color_id = " +
                "                  model_exterior_colors.id " +
                "       INNER JOIN trim_interior_colors " +
                "               ON estimates.trim_interior_color_id = trim_interior_colors.id " +
                "       LEFT JOIN estimate_packages " +
                "              ON estimates.id = estimate_packages.estimate_id " +
                "       LEFT JOIN estimate_options " +
                "              ON estimates.id = estimate_options.estimate_id " +
                "WHERE  detail_trim_id = :detailTrimId " +
                "       AND model_exterior_colors.color_code = :exteriorColorCode " +
                "       AND trim_interior_colors.model_interior_color_code = :interiorColorCode " +
                "GROUP  BY estimates.id " +
                "HAVING Count(DISTINCT model_package_id) = :countOfPackages " +
                "       AND Sum(CASE " +
                "                 WHEN model_package_id IN ( :selectPackageIds ) THEN 1 " +
                "                 ELSE 0 " +
                "               end) = :countOfSumPackages " +
                "       AND Count (DISTINCT model_option_id) = :countOfOptions " +
                "       AND Sum(CASE " +
                "                 WHEN model_option_id IN ( :selectOptionIds ) THEN 1 " +
                "                 ELSE 0 " +
                "               end) = :countOfSumOptions ";

        try {
            return jdbcTemplate.queryForObject(SQL, parameters, Long.class);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
