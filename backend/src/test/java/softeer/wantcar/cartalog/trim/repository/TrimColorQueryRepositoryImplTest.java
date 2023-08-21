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
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("트림 색상 Repository 테스트")
class TrimColorQueryRepositoryImplTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    TrimQueryRepository trimQueryRepository;
    TrimColorQueryRepository trimColorQueryRepository;
    ServerPath serverPath = new ServerPath();
    SoftAssertions softAssertions;

    Long leblancId;

    @BeforeEach
    void setUp() {
        trimColorQueryRepository = new TrimColorQueryRepositoryImpl(serverPath, jdbcTemplate);
        trimQueryRepository = new TrimQueryRepositoryImpl(serverPath, jdbcTemplate);
        softAssertions = new SoftAssertions();
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
            TrimExteriorColorListResponseDto result = trimColorQueryRepository.findTrimExteriorColorByTrimId(leblancId);

            //then
            List<TrimExteriorColorListResponseDto.TrimExteriorColorDto> exteriorColors = result.getExteriorColors();
            softAssertions.assertThat(exteriorColors.size()).isEqualTo(6);

            List<String> resultColorCodes = exteriorColors.stream()
                    .map(TrimExteriorColorListResponseDto.TrimExteriorColorDto::getCode)
                    .collect(Collectors.toList());
            softAssertions.assertThat(resultColorCodes).containsAll(selectableLeBlancExteriorColors);

            List<String> colorImageUrls = exteriorColors.stream()
                    .map(TrimExteriorColorListResponseDto.TrimExteriorColorDto::getColorImageUrl)
                    .collect(Collectors.toList());
            for (String colorImageUrl : colorImageUrls) {
                softAssertions.assertThat(colorImageUrl).startsWith(serverPath.IMAGE_SERVER_PATH);
            }

            List<String> carImageUrls = exteriorColors.stream()
                    .map(TrimExteriorColorListResponseDto.TrimExteriorColorDto::getCarImageDirectory)
                    .collect(Collectors.toList());
            for (String carImageUrl : carImageUrls) {
                softAssertions.assertThat(carImageUrl).startsWith(serverPath.IMAGE_SERVER_PATH);
            }
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
            String exteriorColorCode = "A2B";
            List<String> selectableInteriorColors = List.of("I49", "YJY");


            //when
            TrimInteriorColorListResponseDto result =
                    trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(leblancId, exteriorColorCode);

            //then
            List<TrimInteriorColorListResponseDto.TrimInteriorColorDto> interiorColors = result.getInteriorColors();
            softAssertions.assertThat(result.getInteriorColors().size()).isEqualTo(2);

            softAssertions.assertThat(interiorColors.size()).isEqualTo(2);

            List<String> resultColorCode = interiorColors.stream()
                    .map(TrimInteriorColorListResponseDto.TrimInteriorColorDto::getCode)
                    .collect(Collectors.toList());
            softAssertions.assertThat(resultColorCode).containsAll(selectableInteriorColors);

            List<String> colorImageUrls = interiorColors.stream()
                    .map(TrimInteriorColorListResponseDto.TrimInteriorColorDto::getColorImageUrl)
                    .collect(Collectors.toList());
            for (String colorImageUrl : colorImageUrls) {
                softAssertions.assertThat(colorImageUrl).startsWith(serverPath.IMAGE_SERVER_PATH);
            }

            List<String> carImageUrls = interiorColors.stream()
                    .map(TrimInteriorColorListResponseDto.TrimInteriorColorDto::getCarImageUrl)
                    .collect(Collectors.toList());
            for (String carImageUrl : carImageUrls) {
                softAssertions.assertThat(carImageUrl).startsWith(serverPath.IMAGE_SERVER_PATH);
            }
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않은 트림 식별자로 전달한 경우 null을 반환해야 한다.")
        void failByNonexistentTrimId() {
            //given
            //when
            TrimInteriorColorListResponseDto result =
                    trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(-1L, "A2B");

            //then
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("존재하지 않은 외장 색상 코드로 전달한 경우 null을 반환해야 한다.")
        void failByNonexistentColorCode() {
            //given
            //when
            TrimInteriorColorListResponseDto result =
                    trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(leblancId, "AAA");

            //then
            assertThat(result).isNull();
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
