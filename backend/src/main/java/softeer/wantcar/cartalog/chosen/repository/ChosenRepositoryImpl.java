package softeer.wantcar.cartalog.chosen.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Slf4j
@Repository
@RequiredArgsConstructor
public class ChosenRepositoryImpl implements ChosenRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapperUtils.RowMapperStrategy getIdCodeRowMapperStrategy = new RowMapperUtils.RowMapperStrategy() {
        @Override
        public boolean isMappingTarget(Class<?> type, String name) {
            return Objects.equals(name, "id_code");
        }

        @Override
        public Object mapping(String name, ResultSet rs) throws SQLException {
            return rs.getObject(name);
        }
    };

    @Override
    public List<Integer> findModelTypeChosenByOptionId(List<String> modelTypeIds) {
        if (modelTypeIds.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", modelTypeIds);

        var query = jdbcTemplate.query("SELECT id_code, chosen FROM chosens WHERE domain = 'model_type' ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));
        if (modelTypeIds.size() != query.size()) {
            throw new IllegalArgumentException();
        }
        return query.stream()
                .map(ChosenDto::getChosen)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Integer> findOptionChosenByOptionId(List<String> optionIds) {
        if (optionIds.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", optionIds);

        var query = jdbcTemplate.query("SELECT id_code, chosen FROM chosens WHERE domain = 'model_option' ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));
        if (optionIds.size() != query.size()) {
            throw new IllegalArgumentException();
        }

        return query.stream()
                .map(ChosenDto::getChosen)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Integer> findPackageChosenByOptionId(List<String> packageIds) {
        if (packageIds.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("packageIds", packageIds);

        var query = jdbcTemplate.query("SELECT id_code, chosen FROM chosens WHERE domain = 'model_package' ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));
        if (packageIds.size() != query.size()) {
            throw new IllegalArgumentException();
        }

        return query.stream()
                .map(ChosenDto::getChosen)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Integer> findExteriorColorChosenByExteriorColorCode(List<String> exteriorColorCodes) {
        if (exteriorColorCodes.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("exteriorColorCodes", exteriorColorCodes);

        var query = jdbcTemplate.query("SELECT id_code, chosen FROM chosens WHERE domain = 'exterior_color' ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

        if (exteriorColorCodes.size() != query.size()) {
            throw new IllegalArgumentException();
        }

        return query.stream()
                .map(ChosenDto::getChosen)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Integer> findInteriorColorChosenByInteriorColorCode(String exteriorColorCode, List<String> interiorColorCodes) {
        if (interiorColorCodes.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("exteriorColorCode", exteriorColorCode)
                .addValue("interiorColorCodes", interiorColorCodes);

        var query = jdbcTemplate.query("SELECT id_code, chosen FROM chosens WHERE domain = 'interior_color' ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

        if (interiorColorCodes.size() != query.size()) {
            throw new IllegalArgumentException();
        }

        return query.stream()
                .map(ChosenDto::getChosen)
                .collect(Collectors.toUnmodifiableList());
    }
}
