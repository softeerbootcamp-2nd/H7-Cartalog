package softeer.wantcar.cartalog.trim.repository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.ServerPaths;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrimColorQueryRepositoryImpl implements TrimColorQueryRepository {
    private final ServerPaths serverPaths;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Builder
    private static class TrimExteriorColorQueryResult {
        private String code;
        private String name;
        private String imageUrl;
        private int price;
        private String exteriorImageDirectory;

        private TrimExteriorColorListResponseDto.TrimExteriorColorDto toTrimExteriorColorDto() {
            return TrimExteriorColorListResponseDto.TrimExteriorColorDto.builder()
                    .code(code)
                    .name(name)
                    .colorImageUrl(imageUrl)
                    .price(price)
                    .carImageDirectory(exteriorImageDirectory)
                    .build();
        }
    }

    @Builder
    @ToString
    private static class TrimInteriorColorQueryResult {
        private String code;
        private String name;
        private String imageUrl;
        private int price;
        private String interiorImageUrl;

        private TrimInteriorColorListResponseDto.TrimInteriorColorDto toTrimInteriorColorDto() {
            return TrimInteriorColorListResponseDto.TrimInteriorColorDto.builder()
                    .code(code)
                    .name(name)
                    .colorImageUrl(imageUrl)
                    .price(price)
                    .carImageUrl(interiorImageUrl)
                    .build();
        }
    }

    private TrimExteriorColorQueryResult mappingTrimExteriorColorQueryResult(final ResultSet rs, int rowNumber) throws SQLException {
        return TrimExteriorColorQueryResult.builder()
                .code(rs.getString("code"))
                .name(rs.getString("name"))
                .imageUrl(serverPaths.attachImageServerPath(rs.getString("image_url")))
                .price(rs.getInt("price"))
                .exteriorImageDirectory(serverPaths.attachImageServerPath(rs.getString("exterior_image_directory")))
                .build();
    }

    private TrimInteriorColorQueryResult mappingTrimInteriorColorQueryResult(final ResultSet rs, int rowNumber) throws SQLException {
        return TrimInteriorColorQueryResult.builder()
                .code(rs.getString("code"))
                .name(rs.getString("name"))
                .imageUrl(serverPaths.attachImageServerPath(rs.getString("image_url")))
                .price(rs.getInt("price"))
                .interiorImageUrl(serverPaths.attachImageServerPath(rs.getString("interior_image_url")))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TrimExteriorColorListResponseDto findTrimExteriorColorByTrimId(Long trimId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);

        List<TrimExteriorColorQueryResult> results = jdbcTemplate.query(
                QueryString.findTrimExteriorColorByTrimId, parameters, this::mappingTrimExteriorColorQueryResult);

        if (results.isEmpty()) {
            return null;
        }

        TrimExteriorColorListResponseDto.TrimExteriorColorListResponseDtoBuilder builder = TrimExteriorColorListResponseDto.builder();
        results.stream()
                .map(TrimExteriorColorQueryResult::toTrimExteriorColorDto)
                .forEach(builder::trimExteriorColorDto);

        return builder.build();
    }

    @Override
    public TrimInteriorColorListResponseDto findTrimInteriorColorByTrimIdAndExteriorColorCode(Long trimId, String colorCode) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId)
                .addValue("colorCode", colorCode);

        String SQL = "SELECT " +
                "  model_interior_colors.code, " +
                "  model_interior_colors.name, " +
                "  model_interior_colors.price, " +
                "  model_interior_colors.image_url, " +
                "  model_interior_colors.interior_image_url " +
                "FROM model_interior_colors " +
                "INNER JOIN trim_interior_colors " +
                "ON model_interior_colors.code = trim_interior_colors.model_interior_color_code " +
                "WHERE trim_exterior_color_id = " +
                "  (SELECT trim_exterior_colors.id " +
                "  FROM trim_exterior_colors " +
                "  INNER JOIN model_exterior_colors " +
                "  ON trim_exterior_colors.model_exterior_color_id = model_exterior_colors.id " +
                "  WHERE trim_exterior_colors.trim_id = :trimId " +
                "    AND model_exterior_colors.color_code = :colorCode)";

        List<TrimInteriorColorQueryResult> results = jdbcTemplate.query(
                SQL, parameters, this::mappingTrimInteriorColorQueryResult
        );

        if (results.isEmpty()) {
            return null;
        }

        for (TrimInteriorColorQueryResult result : results) {
            log.info(result.toString());
        }

        TrimInteriorColorListResponseDto.TrimInteriorColorListResponseDtoBuilder builder = TrimInteriorColorListResponseDto.builder();
        results.stream()
                .map(TrimInteriorColorQueryResult::toTrimInteriorColorDto)
                .forEach(builder::trimInteriorColorDto);

        return builder.build();
    }
}
