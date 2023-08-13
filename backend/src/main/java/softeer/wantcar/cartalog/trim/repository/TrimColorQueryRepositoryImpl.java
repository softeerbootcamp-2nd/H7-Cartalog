package softeer.wantcar.cartalog.trim.repository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.ServerPaths;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;

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

    private TrimExteriorColorQueryResult mappingTrimExteriorColorQueryResult(final ResultSet rs, int rowNumber) throws SQLException {
        return TrimExteriorColorQueryResult.builder()
                .code(rs.getString("code"))
                .name(rs.getString("name"))
                .imageUrl(serverPaths.attachImageServerPath(rs.getString("image_url")))
                .price(rs.getInt("price"))
                .exteriorImageDirectory(serverPaths.attachImageServerPath(rs.getString("exterior_image_directory")))
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
}
