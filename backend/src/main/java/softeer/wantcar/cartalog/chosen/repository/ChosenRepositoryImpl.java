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

        String SQL = "SELECT " +
                "    recent.model_option_id AS id_code, " +
                "    recent.recent_records, " +
                "    total.total_records " +
                "FROM " +
                "    (SELECT " +
                "        dmdo.model_option_id, " +
                "        COUNT(dmdo.model_option_id) AS recent_records, " +
                "        mo.child_category " +
                "    FROM " +
                "        release_records " +
                "        INNER JOIN estimates ON estimates.id = release_records.estimate_id " +
                "        INNER JOIN detail_trims ON detail_trims.id = estimates.detail_trim_id " +
                "        INNER JOIN detail_models ON detail_models.id = detail_trims.detail_model_id " +
                "        INNER JOIN detail_model_decision_options AS dmdo ON dmdo.detail_model_id = detail_models.id " +
                "        INNER JOIN model_options AS mo ON mo.id = dmdo.model_option_id " +
                "    WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "    and mo.id in ( :optionIds) " +
                "    GROUP BY " +
                "        dmdo.model_option_id, mo.child_category) AS recent " +
                "INNER JOIN " +
                "    (SELECT " +
                "        mo.child_category, " +
                "        COUNT(mo.child_category) AS total_records " +
                "    FROM " +
                "        release_records " +
                "        INNER JOIN estimates ON estimates.id = release_records.estimate_id " +
                "        INNER JOIN detail_trims ON detail_trims.id = estimates.detail_trim_id " +
                "        INNER JOIN detail_models ON detail_models.id = detail_trims.detail_model_id " +
                "        INNER JOIN detail_model_decision_options AS dmdo ON dmdo.detail_model_id = detail_models.id " +
                "        INNER JOIN model_options AS mo ON mo.id = dmdo.model_option_id " +
                "    WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "    and mo.id in ( :optionIds) " +
                "    GROUP BY " +
                "        mo.child_category) AS total " +
                "ON recent.child_category = total.child_category ";

        var query = jdbcTemplate.query(SQL, parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

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

        String SQL = "SELECT recent_records_count.id_code, recent_records, total_records  " +
                "FROM (  " +
                "    SELECT  " +
                "        model_option_id AS id_code,  " +
                "        COUNT(model_option_id) AS recent_records  " +
                "    FROM release_records  " +
                "    INNER JOIN estimates ON estimates.id = release_records.estimate_id  " +
                "    INNER JOIN estimate_options ON estimate_options.estimate_id = estimates.id  " +
                "    WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "    GROUP BY model_option_id  " +
                ") AS recent_records_count  " +
                "LEFT JOIN (  " +
                "    SELECT  " +
                "        model_options.id AS id_code,  " +
                "        COUNT(model_options.id) AS total_records  " +
                "    FROM release_records  " +
                "    INNER JOIN estimates ON estimates.id = release_records.estimate_id  " +
                "    INNER JOIN detail_trims ON detail_trims.id = estimates.detail_trim_id  " +
                "    INNER JOIN detail_trim_options ON detail_trim_options.detail_trim_id = detail_trims.id  " +
                "    INNER JOIN model_options ON model_options.id = detail_trim_options.model_option_id  " +
                "    WHERE model_options.basic = FALSE  " +
                "        AND release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "    GROUP BY model_options.id  " +
                ") AS total_records_count  " +
                "ON recent_records_count.id_code = total_records_count.id_code " +
                "WHERE recent_records_count.id_code in ( :optionIds ) ";

        var query = jdbcTemplate.query(SQL, parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

        log.info(String.valueOf(query.size()));
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

        String SQL = "SELECT recent_records_count.id_code,  " +
                "       recent_records,  " +
                "       total_records  " +
                "FROM   (SELECT model_package_id        AS id_code,  " +
                "               Count(model_package_id) AS recent_records  " +
                "        FROM   release_records  " +
                "               INNER JOIN estimates  " +
                "                       ON estimates.id = release_records.estimate_id  " +
                "               INNER JOIN estimate_packages  " +
                "                       ON estimate_packages.estimate_id = estimates.id  " +
                "        WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "        GROUP  BY model_package_id) AS recent_records_count  " +
                "       LEFT JOIN (SELECT model_packages.id        AS id_code,  " +
                "                         Count(model_packages.id) AS total_records  " +
                "                  FROM   release_records  " +
                "                         INNER JOIN estimates  " +
                "                                 ON estimates.id = release_records.estimate_id  " +
                "                         INNER JOIN detail_trims  " +
                "                                 ON detail_trims.id = estimates.detail_trim_id  " +
                "                         INNER JOIN detail_trim_packages  " +
                "                                 ON detail_trim_packages.detail_trim_id =  " +
                "                                    detail_trims.id  " +
                "                         INNER JOIN model_packages  " +
                "                                 ON model_packages.id =  " +
                "                                    detail_trim_packages.model_package_id  " +
                "                  WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "                  GROUP  BY model_packages.id) AS total_records_count  " +
                "              ON recent_records_count.id_code = total_records_count.id_code  " +
                "WHERE  recent_records_count.id_code IN ( :packageIds ) ";

        var query = jdbcTemplate.query(SQL, parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

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

        String SQL = "SELECT recent_records_count.id_code,   " +
                "       recent_records,   " +
                "       total_records   " +
                "FROM   (SELECT model_exterior_colors.color_code        AS id_code,   " +
                "               Count(model_exterior_colors.color_code) AS recent_records   " +
                "        FROM   release_records   " +
                "               INNER JOIN estimates   " +
                "                       ON estimates.id = release_records.estimate_id   " +
                "               INNER JOIN trim_exterior_colors   " +
                "                       ON trim_exterior_colors.id = estimates.trim_exterior_color_id   " +
                "               INNER JOIN model_exterior_colors   " +
                "                       ON model_exterior_colors.id = trim_exterior_colors.model_exterior_color_id   " +
                "       WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "           AND model_exterior_colors.color_code IN ( :exteriorColorCodes ) " +
                "       GROUP  BY model_exterior_colors.color_code) AS recent_records_count   " +
                "       CROSS JOIN (SELECT Count(trim_exterior_colors.id) AS total_records   " +
                "                   FROM   release_records   " +
                "                          INNER JOIN estimates   " +
                "                                  ON estimates.id = release_records.estimate_id   " +
                "                          INNER JOIN trim_exterior_colors   " +
                "                                  ON trim_exterior_colors.id = estimates.trim_exterior_color_id   " +
                "                          INNER JOIN model_exterior_colors   " +
                "                                  ON model_exterior_colors.id = trim_exterior_colors.model_exterior_color_id   " +
                "WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY) AS total_records_count ";

        var query = jdbcTemplate.query(SQL, parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

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

        String SQL = "SELECT recent_records_count.id_code, " +
                "       recent_records, " +
                "       total_records " +
                "FROM   (SELECT trim_interior_colors.model_interior_color_code        AS id_code, " +
                "               Count(trim_interior_colors.model_interior_color_code) AS " +
                "               recent_records " +
                "        FROM   release_records " +
                "               INNER JOIN estimates " +
                "                       ON estimates.id = release_records.estimate_id " +
                "               INNER JOIN trim_interior_colors " +
                "                       ON trim_interior_colors.id = " +
                "                          estimates.trim_interior_color_id " +
                "               INNER JOIN trim_exterior_colors " +
                "                       ON trim_exterior_colors.id = " +
                "                          trim_interior_colors.trim_exterior_color_id " +
                "               INNER JOIN model_exterior_colors " +
                "                       ON model_exterior_colors.id = " +
                "                          trim_exterior_colors.model_exterior_color_id " +
                "        WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "               AND model_exterior_colors.color_code = :exteriorColorCode " +
                "               AND trim_interior_colors.model_interior_color_code in ( :interiorColorCodes ) " +
                "        GROUP  BY trim_interior_colors.model_interior_color_code) AS " +
                "       recent_records_count " +
                "       CROSS JOIN (SELECT Count(trim_interior_colors.model_interior_color_code) " +
                "                          AS " +
                "                          total_records " +
                "                   FROM   release_records " +
                "                          INNER JOIN estimates " +
                "                                  ON estimates.id = release_records.estimate_id " +
                "                          INNER JOIN trim_interior_colors " +
                "                                  ON trim_interior_colors.id = " +
                "                                     estimates.trim_interior_color_id " +
                "                          INNER JOIN trim_exterior_colors " +
                "                                  ON trim_exterior_colors.id = " +
                "                                     trim_interior_colors.trim_exterior_color_id " +
                "                          INNER JOIN model_exterior_colors " +
                "                                  ON model_exterior_colors.id = " +
                "trim_exterior_colors.model_exterior_color_id " +
                "WHERE release_records.create_date >= CURRENT_DATE - INTERVAL '" + daysAgo + "' DAY  " +
                "AND model_exterior_colors.color_code = :exteriorColorCode) AS total_records_count ";

        var query = jdbcTemplate.query(SQL, parameters, RowMapperUtils.mapping(ChosenDto.class, getIdCodeRowMapperStrategy));

        if (interiorColorCodes.size() != query.size()) {
            throw new IllegalArgumentException();
        }

        return query.stream()
                .map(ChosenDto::getChosen)
                .collect(Collectors.toUnmodifiableList());
    }
}