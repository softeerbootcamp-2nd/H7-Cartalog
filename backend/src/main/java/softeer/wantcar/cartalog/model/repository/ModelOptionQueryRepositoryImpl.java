package softeer.wantcar.cartalog.model.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.dto.HMGDataDtoInterface;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;
import softeer.wantcar.cartalog.model.dto.ModelTypeDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.dto.OptionDto;
import softeer.wantcar.cartalog.model.repository.dto.SimpleModelOptionMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
@RequiredArgsConstructor
public class ModelOptionQueryRepositoryImpl implements ModelOptionQueryRepository {
    private final ServerPath serverPath;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public ModelTypeListResponseDto findByModelTypeOptionsByTrimId(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);

        List<SimpleModelOptionMapper> simpleModelOptionMapperList = jdbcTemplate.query(QueryString.findByModelTypeOptions, parameters,
                RowMapperUtils.mapping(SimpleModelOptionMapper.class, serverPath.getImageServerPathRowMapperStrategy()));

        if (simpleModelOptionMapperList.isEmpty()) {
            return null;
        }
        return buildModelTypeListResponseDto(simpleModelOptionMapperList);
    }

    @Override
    public List<String> findModelTypeCategoriesByIds(List<Long> modelTypeIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("modelTypeIds", modelTypeIds);

        List<String> categories = jdbcTemplate.query("SELECT child_category AS childCategory FROM model_options WHERE id IN (:modelTypeIds)",
                parameters, (rs, rowNum) -> rs.getString("childCategory"));

        return categories.isEmpty() ? null : categories;
    }

    private ModelTypeListResponseDto buildModelTypeListResponseDto(List<SimpleModelOptionMapper> simpleModelOptionMapperList) {
        Map<String, Map<Long, OptionDto.OptionDtoBuilder>> dtoBuilderMap = new HashMap<>();
        simpleModelOptionMapperList.forEach(mapper -> {
            Map<Long, OptionDto.OptionDtoBuilder> optionDtoBuilderMap = dtoBuilderMap.getOrDefault(mapper.getChildCategory(), new HashMap<>());
            OptionDto.OptionDtoBuilder optionDtoBuilder = optionDtoBuilderMap.getOrDefault(mapper.getModelOptionId(),
                    OptionDto.builder()
                            .id(mapper.getModelOptionId())
                            .name(mapper.getName())
                            .price(mapper.getPrice())
                            .imageUrl(mapper.getImageUrl())
                            .description(mapper.getDescription()));
            HMGDataDtoInterface hmgDataDto = mapper.toHMGDataDto();
            if (hmgDataDto != null) {
                optionDtoBuilder.hmgDatum(mapper.toHMGDataDto());
            }
            optionDtoBuilderMap.put(mapper.getModelOptionId(), optionDtoBuilder);
            dtoBuilderMap.put(mapper.getChildCategory(), optionDtoBuilderMap);
        });

        ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder builder = ModelTypeListResponseDto.builder();
        dtoBuilderMap.forEach((type, optionDtoBuilderMap) -> {
            ModelTypeDto.ModelTypeDtoBuilder modelTypeDtoBuilder = ModelTypeDto.builder().type(type);
            optionDtoBuilderMap.forEach((modelOptionId, optionDtoBuilder) -> modelTypeDtoBuilder.option(optionDtoBuilder.build()));
            builder.modelType(modelTypeDtoBuilder.build());
        });
        return builder.build();
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
