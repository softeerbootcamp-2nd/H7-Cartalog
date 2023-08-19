package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateCountDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateInfoDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionInfoDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionIdListDto;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateQueryRepositoryImpl implements EstimateQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ServerPath serverPath;

    @Override
    public List<EstimateInfoDto> findEstimateInfoBydEstimateIds(List<Long> similarEstimateIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("similarEstimateIds", similarEstimateIds);
        return jdbcTemplate.query(QueryString.findSimilarEstimateInfoByEstimateIds,
                parameters, RowMapperUtils.mapping(EstimateInfoDto.class, serverPath.getImageServerPathRowMapperStrategy()));
    }

    @Override
    public List<EstimateOptionInfoDto> findEstimateOptionsByEstimateIds(List<Long> estimateIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("similarEstimateIds", estimateIds);
        return jdbcTemplate.query(QueryString.findSimilarEstimateOptionsByEstimateIds,
                parameters, (rs, rowNum) -> getEstimateOptionInfoDto(rs, true));
    }

    @Override
    public List<EstimateOptionInfoDto> findEstimatePackagesByEstimateIds(List<Long> estimateIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("similarEstimateIds", estimateIds);
        return jdbcTemplate.query(QueryString.findSimilarEstimatePackagesByEstimateIds,
                parameters, (rs, rowNum) -> getEstimateOptionInfoDto(rs, false));
    }

    @Override
    public EstimateOptionIdListDto findEstimateOptionIdsByEstimateId(Long estimateId) {
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

        return new EstimateOptionIdListDto(queryResult.get(0).getTrimId(), optionIds, packageIds);
    }

    @Override
    public Long findAveragePrice(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);
        return jdbcTemplate.queryForObject(QueryString.findAveragePrice, parameters, Long.class);
    }

    @Override
    public List<EstimateCountDto> findEstimateCounts(List<Long> estimateIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("estimateIds", estimateIds);
        return jdbcTemplate.query(QueryString.findEstimateCounts,
                parameters, RowMapperUtils.mapping(EstimateCountDto.class));
    }

    @Override
    public Long findEstimateIdByEstimateDto(EstimateDto estimateDto) {
        List<Long> selectPackages = estimateDto.getModelPackageIds();
        List<Long> selectOptions = estimateDto.getModelOptionIds();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("detailTrimId", estimateDto.getDetailTrimId())
                .addValue("trimExteriorColorId", estimateDto.getTrimExteriorColorId())
                .addValue("trimInteriorColorId", estimateDto.getTrimInteriorColorId())
                .addValue("selectPackageIds", selectPackages)
                .addValue("selectOptionIds", selectOptions)
                .addValue("countOfPackages", selectPackages.size())
                .addValue("countOfOptions", selectOptions.size())
                .addValue("countOfSumPackages", selectPackages.size() * Math.max(selectOptions.size(), 1))
                .addValue("countOfSumOptions", Math.max(selectPackages.size(), 1) * selectOptions.size());
        try {
            return jdbcTemplate.queryForObject(QueryString.findEstimateIdByEstimateDto, parameters, Long.class);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private EstimateOptionInfoDto getEstimateOptionInfoDto(ResultSet rs, boolean isOption) throws SQLException {
        String optionPrefix = isOption ? "O" : "P";
        return EstimateOptionInfoDto.builder()
                .estimateId(rs.getLong("estimate_id"))
                .optionId(optionPrefix + rs.getLong("option_id"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .imageUrl(serverPath.attachImageServerPath(rs.getString("image_url")))
                .build();
    }
}
