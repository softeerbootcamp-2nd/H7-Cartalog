package softeer.wantcar.cartalog.trim.repository;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.trim.repository.dto.TrimExteriorColorQueryResult;
import softeer.wantcar.cartalog.trim.repository.dto.TrimInteriorColorQueryResult;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("트림 색상 Repository 테스트")
@ExtendWith(SoftAssertionsExtension.class)
class TrimColorQueryRepositoryImplTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    TrimQueryRepository trimQueryRepository;
    TrimColorQueryRepository trimColorQueryRepository;
    ServerPath serverPath = new ServerPath();
    @InjectSoftAssertions
    SoftAssertions softAssertions;

    Long leblancId;

    @BeforeEach
    void setUp() {
        trimColorQueryRepository = new TrimColorQueryRepositoryImpl(serverPath, jdbcTemplate);
        trimQueryRepository = new TrimQueryRepositoryImpl(serverPath, jdbcTemplate);
        leblancId = jdbcTemplate.queryForObject("SELECT id FROM trims WHERE name= 'Le Blanc'", new HashMap<>(), Long.TYPE);
    }

    @Nested
    @DisplayName("외장 색상 리스트 반환 테스트")
    class findTrimExteriorColorByTrimIdTest {
        @Test
        @DisplayName("적절한 트림 식별자를 전달할 경우 해당 트림이 선택 가능한 외장 색상을 반환해야 한다.")
        void success() {
            //given
            List<String> selectableLeBlancExteriorColors = List.of("A2B", "R2T", "UB7", "D2S", "P7V", "WC9");

            //when
            List<TrimExteriorColorQueryResult> trimExteriorColors = trimColorQueryRepository.findTrimExteriorColorsByTrimId(leblancId);

            //then
            List<String> exteriorColorCodes = trimExteriorColors.stream()
                    .map(TrimExteriorColorQueryResult::getCode)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(exteriorColorCodes.containsAll(selectableLeBlancExteriorColors)).isTrue();

            boolean imageUrlCheck = trimExteriorColors.stream()
                    .allMatch(trimExteriorColor -> trimExteriorColor.getImageUrl().startsWith(serverPath.IMAGE_SERVER_PATH));
            boolean exteriorImageDirectoryCheck = trimExteriorColors.stream()
                    .allMatch(trimExteriorColor -> trimExteriorColor.getExteriorImageDirectory().startsWith(serverPath.IMAGE_SERVER_PATH));
            softAssertions.assertThat(imageUrlCheck).isTrue();
            softAssertions.assertThat(exteriorImageDirectoryCheck).isTrue();
        }

        @Test
        @DisplayName("존재하지 않은 트림 식별자로 전달한 경우 빈 리스트를 반환해야 한다.")
        void failByNonexistentTrimId() {
            //given
            //when
            List<TrimExteriorColorQueryResult> trimExteriorColors = trimColorQueryRepository.findTrimExteriorColorsByTrimId(-1L);

            //then
            assertThat(trimExteriorColors.isEmpty()).isTrue();

        }
    }

    @Nested
    @DisplayName("내장 색상 리스트 반환 테스트")
    class findTrimInteriorColorByTrimIdAndExteriorColorCode {
        @Test
        @DisplayName("적절한 트림 아이디와 외장 색상 코드를 전달했을 때 해당 조합으로 선택 가능한 내장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            String exteriorColorCode = "A2B";
            List<String> selectableInteriorColors = List.of("I49", "YJY");


            //when
            List<TrimInteriorColorQueryResult> trimInteriorColors = trimColorQueryRepository.findTrimInteriorColorsByTrimIdAndExteriorColorCode(leblancId, exteriorColorCode);

            //then
            List<String> interiorColorCodes = trimInteriorColors.stream()
                    .map(TrimInteriorColorQueryResult::getCode)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(interiorColorCodes.containsAll(selectableInteriorColors)).isTrue();

            boolean imageUrlCheck = trimInteriorColors.stream()
                    .allMatch(trimInteriorColor -> trimInteriorColor.getImageUrl().startsWith(serverPath.IMAGE_SERVER_PATH));
            boolean interiorImageUrlCheck = trimInteriorColors.stream()
                    .allMatch(trimExteriorColor -> trimExteriorColor.getInteriorImageUrl().startsWith(serverPath.IMAGE_SERVER_PATH));
            softAssertions.assertThat(imageUrlCheck).isTrue();
            softAssertions.assertThat(interiorImageUrlCheck).isTrue();
        }

        @Test
        @DisplayName("존재하지 않은 트림 식별자로 전달한 경우 빈 리스트를 반환해야 한다.")
        void failByNonexistentTrimId() {
            //given
            //when

            List<TrimInteriorColorQueryResult> trimInteriorColors = trimColorQueryRepository.findTrimInteriorColorsByTrimIdAndExteriorColorCode(-1L, "A2B");

            //then
            assertThat(trimInteriorColors.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("존재하지 않은 외장 색상 코드로 전달한 경우 빈 리스트를 반환해야 한다.")
        void failByNonexistentColorCode() {
            //given
            //when

            List<TrimInteriorColorQueryResult> trimInteriorColors = trimColorQueryRepository.findTrimInteriorColorsByTrimIdAndExteriorColorCode(leblancId, "AAA");

            //then
            assertThat(trimInteriorColors.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("트림 식별자와 외장 색상 코드로 트림 외장 색상 식별자 조회 테스트")
    class findTrimExteriorColorIdByTrimIdAndColorCodeTest {
        @Test
        @DisplayName("적절한 입력 시 성공하여야 한다.")
        void success() {
            //given

            //when
            Long a2B = trimColorQueryRepository.findTrimExteriorColorIdByTrimIdAndColorCode(2L, "A2B");

            //then
            assertThat(a2B).isEqualTo(7L);
        }

        @Test
        @DisplayName("적절한 입력이 아니면 null 반환해야 한다.")
        void returnNull() {
            //given

            //when
            Long a2BB = trimColorQueryRepository.findTrimExteriorColorIdByTrimIdAndColorCode(2L, "A2BB");

            //then
            assertThat(a2BB).isNull();
        }
    }

    @Nested
    @DisplayName("트림 식별자와 외장 색상 코드, 내장 색상 코드로 트림 내장 색상 식별자 조회 테스트")
    class findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCodeTest {
        @Test
        @DisplayName("적절한 입력 시 성공해야 한다.")
        void success() {
            //given

            //when
            Long i49 = trimColorQueryRepository.findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(2L, "A2B", "I49");

            //then
            assertThat(i49).isEqualTo(7L);
        }

        @Test
        @DisplayName("적절한 입력이 아니면 null 반환해야 한다.")
        void returnNull() {
            //given

            //when
            Long i49 = trimColorQueryRepository.findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(2L, "A2B1", "I491");

            //then
            assertThat(i49).isNull();
        }
    }

}
