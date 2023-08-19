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
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    @Override
    public Long findEstimateIdByEstimateDto(EstimateDto estimateDto) {
        List<Long> selectPackageIds = estimateDto.getModelPackageIds();
        List<Long> selectOptionIds = estimateDto.getModelOptionIds();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", estimateDto.getDetailTrimId())
                .addValue("exteriorColorCode", estimateDto.getTrimExteriorColorId())
                .addValue("interiorColorCode", estimateDto.getTrimInteriorColorId())
                .addValue("selectPackageIds", selectPackageIds)
                .addValue("selectOptionIds", selectOptionIds)
                .addValue("countOfPackages", selectPackageIds.size())
                .addValue("countOfOptions", selectOptionIds.size())
                .addValue("countOfSumPackages", selectPackageIds.size() * Math.max(selectOptionIds.size(), 1))
                .addValue("countOfSumOptions", Math.max(selectPackageIds.size(), 1) * selectOptionIds.size());

        try {
            return jdbcTemplate.queryForObject(QueryString.findEstimateIdByEstimateDto, parameters, Long.class);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
