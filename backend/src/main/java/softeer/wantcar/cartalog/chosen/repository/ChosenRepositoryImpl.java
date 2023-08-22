package softeer.wantcar.cartalog.chosen.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import softeer.wantcar.cartalog.chosen.ChosenConfig;
import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private List<Object> switchActivateChosen(List<Object> requestIdCodes) {
        if (!ChosenConfig.isActivate) {
            int size = requestIdCodes.size();

            ArrayList<Object> objects = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                objects.add(0);
            }

            return objects;
        }
        return requestIdCodes;
    }

    @Override
    public List<Integer> findModelTypeChosenByOptionId(List<Long> modelTypeIds, int daysAgo) {
        if (!ChosenConfig.isActivate) {
            int size = modelTypeIds.size();

            List<Integer> objects = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                objects.add(0);
            }

            return objects;
        }

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
    public List<Integer> findOptionChosenByOptionId(List<Long> optionIds, int daysAgo) {
        if (!ChosenConfig.isActivate) {
            int size = optionIds.size();

            List<Integer> objects = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                objects.add(0);
            }

            return objects;
        }

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
    public List<Integer> findPackageChosenByOptionId(List<Long> packageIds, int daysAgo) {
        if (!ChosenConfig.isActivate) {
            int size = packageIds.size();

            List<Integer> objects = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                objects.add(0);
            }

            return objects;
        }

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
    public List<Integer> findExteriorColorChosenByExteriorColorCode(List<String> exteriorColorCodes, int daysAgo) {
        if (!ChosenConfig.isActivate) {
            int size = exteriorColorCodes.size();

            List<Integer> objects = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                objects.add(0);
            }

            return objects;
        }

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
    public List<Integer> findInteriorColorChosenByInteriorColorCode(String exteriorColorCode, List<String> interiorColorCodes, int daysAgo) {
        if (!ChosenConfig.isActivate) {
            int size = interiorColorCodes.size();

            List<Integer> objects = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                objects.add(0);
            }

            return objects;
        }

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
