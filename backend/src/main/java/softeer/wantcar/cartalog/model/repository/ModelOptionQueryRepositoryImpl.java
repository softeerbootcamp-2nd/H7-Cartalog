package softeer.wantcar.cartalog.model.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;
import softeer.wantcar.cartalog.model.repository.dto.ModelTypeDto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
@RequiredArgsConstructor
public class ModelOptionQueryRepositoryImpl implements ModelOptionQueryRepository {
    private final ServerPath serverPath;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<ModelTypeDto> findModelTypeByTrimId(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);

        List<ModelTypeDto> modelTypeDtoList = jdbcTemplate.query(QueryString.findByModelTypeOptions, parameters,
                RowMapperUtils.mapping(ModelTypeDto.class, serverPath.getImageServerPathRowMapperStrategy()));

        if (modelTypeDtoList.isEmpty()) {
            return new ArrayList<>();
        }
        return modelTypeDtoList;
    }

    @Override
    public List<String> findModelTypeCategoriesByIds(List<Long> modelTypeIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("modelTypeIds", modelTypeIds);

        List<String> categories = jdbcTemplate.query("SELECT child_category AS childCategory FROM model_options WHERE id IN (:modelTypeIds)",
                parameters, (rs, rowNum) -> rs.getString("childCategory"));

        return categories.isEmpty() ? null : categories;
    }

    @Override
    public List<String> findHashTagFromOptionsByOptionIds(List<Long> optionIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", optionIds);
        return jdbcTemplate.queryForList(QueryString.findHashTagFromOptionsByOptionIds, parameters, String.class);
    }

    @Override
    public List<String> findHashTagFromPackagesByPackageIds(List<Long> packageIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("packageIds", packageIds);
        return jdbcTemplate.queryForList(QueryString.findHashTagFromPackagesByPackageIds, parameters, String.class);
    }
}
