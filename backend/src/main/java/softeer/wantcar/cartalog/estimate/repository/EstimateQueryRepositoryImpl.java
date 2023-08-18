package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateQueryRepositoryImpl implements EstimateQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
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
                .addValue("countValue", selectPackages.size() * selectOptions.size());

        String SQL = "SELECT estimates.id " +
                "FROM   estimates " +
                "       INNER JOIN trim_exterior_colors " +
                "               ON estimates.trim_exterior_color_id = trim_exterior_colors.id " +
                "       INNER JOIN model_exterior_colors " +
                "               ON trim_exterior_colors.model_exterior_color_id = " +
                "                  model_exterior_colors.id " +
                "       INNER JOIN trim_interior_colors " +
                "               ON estimates.trim_interior_color_id = trim_interior_colors.id " +
                "       INNER JOIN estimate_packages " +
                "               ON estimates.id = estimate_packages.estimate_id " +
                "       INNER JOIN estimate_options " +
                "               ON estimates.id = estimate_options.estimate_id " +
                "WHERE  detail_trim_id = :detailTrimId " +
                "       AND model_exterior_colors.color_code = :exteriorColorCode " +
                "       AND trim_interior_colors.model_interior_color_code = :interiorColorCode " +
                "       AND model_package_id IN ( :selectPackageIds ) " +
                "       AND model_option_id IN ( :selectOptionIds ) " +
                "GROUP  BY estimates.id " +
                "HAVING Count(estimates.id) = :countValue ";

        try {
            return jdbcTemplate.queryForObject(SQL, parameters, Long.class);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
