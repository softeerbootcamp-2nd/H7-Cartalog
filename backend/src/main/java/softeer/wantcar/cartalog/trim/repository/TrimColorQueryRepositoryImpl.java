package softeer.wantcar.cartalog.trim.repository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
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

        List<TrimInteriorColorQueryResult> results = jdbcTemplate.query(
                QueryString.findTrimInteriorColorByTrimIdAndExteriorColorCode, parameters, this::mappingTrimInteriorColorQueryResult
        );

        if (results.isEmpty()) {
            return null;
        }

        TrimInteriorColorListResponseDto.TrimInteriorColorListResponseDtoBuilder builder = TrimInteriorColorListResponseDto.builder();
        results.stream()
                .map(TrimInteriorColorQueryResult::toTrimInteriorColorDto)
                .forEach(builder::trimInteriorColorDto);

        return builder.build();
    }
}
