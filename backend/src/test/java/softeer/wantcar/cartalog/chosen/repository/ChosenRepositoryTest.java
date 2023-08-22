package softeer.wantcar.cartalog.chosen.repository;

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
import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("선택률 Repository 테스트")
@ExtendWith(SoftAssertionsExtension.class)
class ChosenRepositoryTest {
    @InjectSoftAssertions
    SoftAssertions softAssertions;
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    ChosenRepository chosenRepository;

    @BeforeEach
    void setUp() {
        chosenRepository = new ChosenRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("모델 타입 옵션 선택률 조회 테스트")
    class findModelTypeChosenByOptionIdTest {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<String> modelTypeIds = List.of("O1", "O2", "O3", "O4", "O5", "O6");
            List<Integer> expectResults = List.of(50, 50, 50, 50, 50, 50);

            //when
            List<Integer> chosenValues = chosenRepository.findModelTypeChosenByOptionId(modelTypeIds).stream()
                    .map(ChosenDto::getChosen)
                    .collect(Collectors.toList());

            //then
            softAssertions.assertThat(chosenValues).containsAll(expectResults);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<String> modelTypeIds = List.of("NOT-EXIST");

            //when
            //then
            assertThatThrownBy(() -> chosenRepository.findModelTypeChosenByOptionId(modelTypeIds))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("식별자를 전달하지 않으면 빈 리스트를 반환하여야 한다.")
        void returnEmptyList() {
            //given
            //when
            List<ChosenDto> modelTypeChosen = chosenRepository.findModelTypeChosenByOptionId(List.of());

            //then
            assertThat(modelTypeChosen.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("모델 옵션 선택률 조회 테스트")
    class findOptionChosenByOptionIdTest {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<String> modelSelectOptionIds = List.of("O97", "O98", "O99", "O100", "O101", "O102", "O103", "O104", "O105", "O106", "O107");
            List<Integer> expectResults = List.of(42, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21);

            //when
            List<Integer> chosenValues = chosenRepository.findOptionChosenByOptionId(modelSelectOptionIds).stream()
                    .map(ChosenDto::getChosen)
                    .collect(Collectors.toList());

            //then
            softAssertions.assertThat(chosenValues).isEqualTo(expectResults);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<String> modelOptionIds = List.of("NOT-EXIST");

            //when
            //then
            assertThatThrownBy(() -> chosenRepository.findOptionChosenByOptionId(modelOptionIds))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("식별자를 전달하지 않으면 빈 리스트를 반환하여야 한다.")
        void returnEmptyList() {
            //given
            //when
            List<ChosenDto> modelOptionChosen = chosenRepository.findOptionChosenByOptionId(List.of());

            //then
            assertThat(modelOptionChosen.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("모델 패키지 선택률 조회 테스트")
    class findPackageChosenByOptionId {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<String> modelPackageIds = List.of("P1", "P2", "P3", "P4");
            List<Integer> expectResults = List.of(21, 21, 21, 21);

            //when
            List<Integer> chosenValues = chosenRepository.findPackageChosenByOptionId(modelPackageIds).stream()
                    .map(ChosenDto::getChosen)
                    .collect(Collectors.toList());

            //then
            softAssertions.assertThat(chosenValues).isEqualTo(expectResults);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<String> modelPackageIds = List.of("NOT-EXIST");

            //when
            //then
            assertThatThrownBy(() -> chosenRepository.findPackageChosenByOptionId(modelPackageIds))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("식별자를 전달하지 않으면 빈 리스트를 반환하여야 한다.")
        void returnEmptyList() {
            //given
            //when
            List<ChosenDto> modelPackageChosen = chosenRepository.findPackageChosenByOptionId(List.of());

            //then
            assertThat(modelPackageChosen.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("모델 외장 색상 선택률 조회 테스트")
    class findExteriorColorChosenByExteriorColorCode {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<String> exteriorColorIds = List.of("A2B", "D2S", "P7V", "R2T", "UB7", "WC9");
            List<Integer> expectResults = List.of(17, 17, 17, 17, 17, 17);

            //when
            List<Integer> chosenValues = chosenRepository.findExteriorColorChosenByExteriorColorCode(exteriorColorIds).stream()
                    .map(ChosenDto::getChosen)
                    .collect(Collectors.toList());

            //then
            softAssertions.assertThat(chosenValues).isEqualTo(expectResults);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<String> exteriorColorIds = List.of("AAA");

            //when
            //then
            assertThatThrownBy(() -> chosenRepository.findExteriorColorChosenByExteriorColorCode(exteriorColorIds))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("식별자를 전달하지 않으면 빈 리스트를 반환하여야 한다.")
        void returnEmptyList() {
            //given
            //when
            List<ChosenDto> exteriorColorChosen = chosenRepository.findExteriorColorChosenByExteriorColorCode(List.of());

            //then
            assertThat(exteriorColorChosen.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("모델 내장 색상 선택률 조회 테스트")
    class findInteriorColorChosenByInteriorColorCode {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<String> interiorColorIds = List.of("I49", "YJY");
            List<Integer> expectResults = List.of(50, 50);

            //when
            List<Integer> chosenValues = chosenRepository.findInteriorColorChosenByInteriorColorCode(interiorColorIds).stream()
                    .map(ChosenDto::getChosen)
                    .collect(Collectors.toList());

            //then
            softAssertions.assertThat(chosenValues).isEqualTo(expectResults);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<String> interiorColorIds = List.of("AAA");

            //when
            //then
            assertThatThrownBy(() -> chosenRepository.findInteriorColorChosenByInteriorColorCode(interiorColorIds))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("식별자를 전달하지 않으면 빈 리스트를 반환하여야 한다.")
        void returnEmptyList() {
            //given
            //when
            List<ChosenDto> interiorColorChosen = chosenRepository.findInteriorColorChosenByInteriorColorCode(List.of());

            //then
            assertThat(interiorColorChosen.size()).isEqualTo(0);
        }
    }
}
