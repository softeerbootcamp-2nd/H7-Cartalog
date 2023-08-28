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
    public List<ChosenDto> findModelTypeChosenByOptionId(List<String> modelTypeIds) {
        if (modelTypeIds.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", modelTypeIds);

        List<ChosenDto> chosenDtoList = jdbcTemplate.query("SELECT id_code, chosen FROM chosens " +
                                       "WHERE domain = 'model_type' AND id_code IN (:optionIds) ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));
        if (modelTypeIds.size() != chosenDtoList.size()) {
            throw new IllegalArgumentException();
        }
        return chosenDtoList;
    }

    @Override
    public List<ChosenDto> findOptionChosenByOptionId(List<String> optionIds) {
        if (optionIds.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("optionIds", optionIds);

        List<ChosenDto> chosenDtoList = jdbcTemplate.query("SELECT id_code, chosen FROM chosens " +
                                       "WHERE domain = 'model_option' AND id_code IN (:optionIds) ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));
        if (optionIds.size() != chosenDtoList.size()) {
            throw new IllegalArgumentException();
        }

        return chosenDtoList;
    }

    @Override
    public List<ChosenDto> findPackageChosenByOptionId(List<String> packageIds) {
        if (packageIds.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("packageIds", packageIds);

        List<ChosenDto> chosenDtoList = jdbcTemplate.query("SELECT id_code, chosen FROM chosens " +
                                       "WHERE domain = 'model_package' AND id_code IN (:packageIds) ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));
        if (packageIds.size() != chosenDtoList.size()) {
            throw new IllegalArgumentException();
        }

        return chosenDtoList;
    }

    @Override
    public List<ChosenDto> findExteriorColorChosenByExteriorColorCode(List<String> exteriorColorCodes) {
        if (exteriorColorCodes.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("exteriorColorCodes", exteriorColorCodes);

        List<ChosenDto> chosenDtoList = jdbcTemplate.query("SELECT id_code, chosen FROM chosens " +
                                       "WHERE domain = 'exterior_color' AND id_code IN (:exteriorColorCodes) ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

        if (exteriorColorCodes.size() != chosenDtoList.size()) {
            throw new IllegalArgumentException();
        }

        return chosenDtoList;
    }

    @Override
    public List<ChosenDto> findInteriorColorChosenByInteriorColorCode(List<String> interiorColorCodes) {
        if (interiorColorCodes.size() == 0) {
            return List.of();
        }
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("interiorColorCodes", interiorColorCodes);

        List<ChosenDto> chosenDtoList = jdbcTemplate.query("SELECT id_code, chosen FROM chosens " +
                                       "WHERE domain = 'interior_color' AND id_code IN (:interiorColorCodes) ",
                parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

        if (interiorColorCodes.size() != chosenDtoList.size()) {
            throw new IllegalArgumentException();
        }

        return chosenDtoList;
    }
}
