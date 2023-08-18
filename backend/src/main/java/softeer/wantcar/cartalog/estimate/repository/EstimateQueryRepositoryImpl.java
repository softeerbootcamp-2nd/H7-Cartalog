package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.EstimateSaveDto;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateQueryRepositoryImpl implements EstimateQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long findEstimateIdByRequestDto(EstimateSaveDto estimateSaveDto) {
        List<Long> selectPackages = estimateSaveDto.getModelPackageIds();
        List<Long> selectOptions = estimateSaveDto.getModelOptionIds();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", estimateSaveDto.getDetailTrimId())
                .addValue("trimExteriorColorId", estimateSaveDto.getTrimExteriorColorId())
                .addValue("trimInteriorColorId", estimateSaveDto.getTrimInteriorColorId())
                .addValue("selectPackageIds", selectPackages)
                .addValue("selectOptionIds", selectOptions)
                .addValue("countOfPackages", selectPackages.size())
                .addValue("countOfOptions", selectOptions.size())
                .addValue("countOfSumPackages", selectPackages.size() * Math.max(selectOptions.size(), 1))
                .addValue("countOfSumOptions", Math.max(selectPackages.size(), 1) * selectOptions.size());

        String SQL = "SELECT estimates.id " +
                "FROM   estimates " +
                "       LEFT JOIN estimate_packages " +
                "              ON estimates.id = estimate_packages.estimate_id " +
                "       LEFT JOIN estimate_options " +
                "              ON estimates.id = estimate_options.estimate_id " +
                "WHERE  detail_trim_id = :detailTrimId " +
                "       AND estimates.trim_exterior_color_id = :trimExteriorColorId " +
                "       AND estimates.trim_interior_color_id = :trimInteriorColorId " +
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
