package softeer.wantcar.cartalog.trim.repository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrimColorQueryRepositoryImpl implements TrimColorQueryRepository {
    @Value("${env.imageServerPath}")
    private static String imageServerPath = "example-url";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Builder
    private static class TrimExteriorColorQueryResult {
        private String code;
        private String name;
        private String imageUrl;
        private int price;
        private String exteriorImageUrl;

        private TrimExteriorColorListResponseDto.TrimExteriorColorDto toTrimExteriorColorDto() {
            return TrimExteriorColorListResponseDto.TrimExteriorColorDto.builder()
                    .code(code)
                    .name(name)
                    .colorImageUrl(imageUrl)
                    .price(price)
                    .carImageUrl(exteriorImageUrl)
                    .build();
        }

        private static TrimExteriorColorQueryResult mapping(final ResultSet rs, int rowNumber) throws SQLException {
            return TrimExteriorColorQueryResult.builder()
                    .code(rs.getString("code"))
                    .name(rs.getString("name"))
                    .imageUrl(imageServerPath + "/" + rs.getString("image_url"))
                    .price(rs.getInt("price"))
                    .exteriorImageUrl(imageServerPath + "/" + rs.getString("exterior_image_url"))
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TrimExteriorColorListResponseDto findTrimExteriorColorByTrimId(Long trimId) {
        String SQL = "SELECT " +
                "  code, " +
                "  name, " +
                "  image_url, " +
                "  price, " +
                "  exterior_image_url " +
                "FROM trim_exterior_colors " +
                "INNER JOIN model_exterior_colors " +
                "ON trim_exterior_colors.model_exterior_color_id = model_exterior_colors.id " +
                "INNER JOIN colors " +
                "  ON model_exterior_colors.color_code = colors.code " +
                "WHERE trim_id = :trimId";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("trimId", trimId);

        List<TrimExteriorColorQueryResult> results = jdbcTemplate.query(SQL, parameters, TrimExteriorColorQueryResult::mapping);
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
