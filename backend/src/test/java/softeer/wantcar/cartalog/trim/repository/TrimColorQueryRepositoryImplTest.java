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
import softeer.wantcar.cartalog.global.ServerPaths;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
class TrimColorQueryRepositoryImplTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    TrimTestRepository trimQueryRepository;
    TrimColorQueryRepository trimColorQueryRepository;
    ServerPaths serverPaths = new ServerPaths();
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        trimColorQueryRepository = new TrimColorQueryRepositoryImpl(serverPaths, jdbcTemplate);
        trimQueryRepository = new TrimTestRepository(jdbcTemplate);
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("외장 색상 리스트 반환 테스트")
    class findTrimExteriorColorByTrimIdTest {
        @Test
        @DisplayName("적절한 트림 식별자를 전달할 경우 해당 트림이 선택 가능한 외장 색상을 반환해야 한다.")
        void success() {
            //given
            Long LeBlancTrimId = trimQueryRepository.findTrimIdByModelNameAndTrimName("팰리세이드", "Le Blanc");
            List<String> selectableLeBlancExteriorColors = List.of("A2B", "R2T", "UB7", "D2S", "P7V", "WC9");

            //when
            TrimExteriorColorListResponseDto result = trimColorQueryRepository.findTrimExteriorColorByTrimId(LeBlancTrimId);

            //then
            softAssertions.assertThat(result.getExteriorColors().size()).isEqualTo(6);
            softAssertions.assertThat(result.hasColor(selectableLeBlancExteriorColors)).isTrue();
            softAssertions.assertThat(result.startWithUrl(serverPaths.IMAGE_SERVER_PATH)).isTrue();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않은 트림 식별자로 전달한 경우 null을 반환해야 한다.")
        void failByNonexistentTrimId() {
            //given
            //when
            TrimExteriorColorListResponseDto result = trimColorQueryRepository.findTrimExteriorColorByTrimId(-1L);

            //then
            assertThat(result).isNull();

        }
    }

    @Nested
    @DisplayName("내장 색상 리스트 반환 테스트")
    class findTrimInteriorColorByTrimIdAndExteriorColorCode {
        @Test
        @DisplayName("적절한 트림 아이디와 외장 색상 코드를 전달했을 때 해당 조합으로 선택 가능한 내장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            Long LeBlancTrimId = trimQueryRepository.findTrimIdByModelNameAndTrimName("팰리세이드", "Le Blanc");
            String exteriorColorCode = "A2B";
            List<String> selectableInteriorColors = List.of("I49", "YJY");


            //when
            TrimInteriorColorListResponseDto result = trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(LeBlancTrimId, exteriorColorCode);

            //then
            softAssertions.assertThat(result.getInteriorColors().size()).isEqualTo(2);
            softAssertions.assertThat(result.hasColor(selectableInteriorColors)).isTrue();
            softAssertions.assertThat(result.startWithUrl(serverPaths.IMAGE_SERVER_PATH)).isTrue();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않은 트림 식별자로 전달한 경우 null을 반환해야 한다.")
        void failByNonexistentTrimId() {
            //given
            //when
            TrimInteriorColorListResponseDto result = trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(-1L, "A2B");

            //then
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("존재하지 않은 외장 색상 코드로 전달한 경우 null을 반환해야 한다.")
        void failByNonexistentColorCode() {
            //given
            Long LeBlancTrimId = trimQueryRepository.findTrimIdByModelNameAndTrimName("팰리세이드", "Le Blanc");

            //when
            TrimInteriorColorListResponseDto result = trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(LeBlancTrimId, "AAA");

            //then
            assertThat(result).isNull();
        }
    }

}