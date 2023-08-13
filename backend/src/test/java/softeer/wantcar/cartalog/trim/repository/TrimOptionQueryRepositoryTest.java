package softeer.wantcar.cartalog.trim.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
class TrimOptionQueryRepositoryTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    TrimOptionQueryRepository trimOptionQueryRepository;
    ServerPath serverPath = new ServerPath();
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        trimOptionQueryRepository = new TrimOptionQueryRepositoryImpl(jdbcTemplate);
        softAssertions = new SoftAssertions();
    }

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
    @Nested
    @DisplayName("트림 옵션 목록 조회 테스트")
    class getTrimOptionListTest {
        @Test
        @DisplayName("존재하는 세부 트림 식별자라면 트림 옵션 목록을 리스트로 반환한다")
        void returnTrimOptionListWhenDetailTrimIdExist() {
            //given
            Long leblancDetailTrimId = jdbcTemplate.queryForObject(
                    "SELECT " +
                    "   dt.id " +
                    "   FROM detail_trims AS dt " +
                    "   JOIN trims AS t ON t.id=dt.trim_id " +
                    "   WHERE t.name= 'Le Blanc' " +
                    "   LIMIT 1",
                    new HashMap<>(), Long.TYPE);

            //when
            List<TrimOptionListResponseDto.TrimOptionDto> trimOptions
                    = trimOptionQueryRepository.findOptionsByDetailTrimId(leblancDetailTrimId);

            softAssertions.assertThat(trimOptions.isEmpty()).isFalse();
            softAssertions.assertThat(trimOptions.size()).isEqualTo(111);
            for (TrimOptionListResponseDto.TrimOptionDto trimOption : trimOptions) {
                softAssertions.assertThat(trimOption.getImageUrl()).startsWith(serverPath.IMAGE_SERVER_PATH);
            }
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 세부 트림 식별자라면 null을 반환한다")
        void returnNullWhenDetailTrimIdNotExist() {
            //given
            Long notExistDetailTrimId = -1L;

            //when
            List<TrimOptionListResponseDto.TrimOptionDto> trimOptions
                    = trimOptionQueryRepository.findOptionsByDetailTrimId(leblancDetailTrimId);

            assertThat(trimOptions).isNull();
        }
    }
}
