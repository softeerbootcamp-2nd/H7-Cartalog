package softeer.wantcar.cartalog.chosen.repository;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    int intDayDifference = 0;

    @BeforeEach
    void setUp() {
        LocalDate currentTime = LocalDate.now();
        LocalDate baseTime = LocalDate.of(2023, 8, 20);

        long dayDifference = ChronoUnit.DAYS.between(baseTime, currentTime);
        intDayDifference = Math.toIntExact(dayDifference);
        chosenRepository = new ChosenRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("모델 타입 옵션 선택률 조회 테스트")
    class findModelTypeChosenByOptionIdTest {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<Long> modelTypeIds = List.of(1L, 2L, 3L, 4L, 5L, 6L);

            int daysAgo = 90 + intDayDifference;

            List<Integer> modelTypeRecentRecords = List.of(126, 115, 126, 115, 126, 115);
            List<Integer> modelTypeTotalRecords = List.of(241, 241, 241, 241, 241, 241);

            List<Integer> collect = IntStream.range(0, 6)
                    .mapToDouble(i -> (double) modelTypeRecentRecords.get(i) * 100 / modelTypeTotalRecords.get(i))
                    .mapToLong(Math::round)
                    .mapToInt(operand -> (int) operand)
                    .boxed()
                    .collect(Collectors.toUnmodifiableList());

            //when
            List<Integer> modelTypeChosen = chosenRepository.findModelTypeChosenByOptionId(modelTypeIds, daysAgo);

            //then
            softAssertions.assertThat(modelTypeChosen).isEqualTo(collect);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<Long> modelTypeIds = List.of(10L);
            int daysAgo = 90 + intDayDifference;

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> chosenRepository.findModelTypeChosenByOptionId(modelTypeIds, daysAgo);

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("모델 옵션 선택률 조회 테스트")
    class findOptionChosenByOptionIdTest {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<Long> modelSelectOptionIds = List.of(97L, 98L, 99L, 100L, 101L, 102L, 103L, 104L, 105L, 106L, 107L);
            int daysAgo = 90 + intDayDifference;

            List<Integer> modelSelectOptionRecords = List.of(57, 34, 42, 53, 41, 45, 39, 40, 48, 42, 40);
            List<Integer> modelSelectOptionTotalRecords = List.of(241, 241, 241, 126, 241, 241, 241, 241, 241, 241, 241);

            List<Integer> modelSelectOptionChosenExpected = IntStream.range(0, modelSelectOptionIds.size())
                    .mapToDouble(i -> (double) modelSelectOptionRecords.get(i) * 100 / modelSelectOptionTotalRecords.get(i))
                    .mapToLong(Math::round)
                    .mapToInt(operand -> (int) operand)
                    .boxed()
                    .collect(Collectors.toUnmodifiableList());

            //when
            List<Integer> modelSelectOptionChosen = chosenRepository.findOptionChosenByOptionId(modelSelectOptionIds, daysAgo);

            //then
            softAssertions.assertThat(modelSelectOptionChosen).isEqualTo(modelSelectOptionChosenExpected);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<Long> modelOptionIds = List.of(10L);
            int daysAgo = 90 + intDayDifference;

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> chosenRepository.findOptionChosenByOptionId(modelOptionIds, daysAgo);

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("모델 패키지 선택률 조회 테스트")
    class findPackageChosenByOptionId {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<Long> modelPackageIds = List.of(1L, 2L, 3L, 4L);
            int daysAgo = 90 + intDayDifference;

            List<Integer> modelPackageRecentRecords = List.of(50, 52, 56, 57);
            List<Integer> modelPackageTotalRecords = List.of(241, 241, 241, 241);

            List<Integer> modelPackageChosenExpected = IntStream.range(0, modelPackageIds.size())
                    .mapToDouble(i -> (double) modelPackageRecentRecords.get(i) * 100 / modelPackageTotalRecords.get(i))
                    .mapToLong(Math::round)
                    .mapToInt(operand -> (int) operand)
                    .boxed()
                    .collect(Collectors.toUnmodifiableList());

            //when
            List<Integer> modelPackageChosen = chosenRepository.findPackageChosenByOptionId(modelPackageIds, daysAgo);

            //then
            softAssertions.assertThat(modelPackageChosen).isEqualTo(modelPackageChosenExpected);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<Long> modelPackageIds = List.of(10L);
            int daysAgo = 90 + intDayDifference;

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> chosenRepository.findPackageChosenByOptionId(modelPackageIds, daysAgo);

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("모델 패키지 선택률 조회 테스트")
    class findExteriorColorChosenByExteriorColorCode {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<String> exteriorColorIds = List.of("A2B", "D2S", "P7V", "R2T", "UB7", "WC9");
            int daysAgo = 90 + intDayDifference;

            List<Integer> exteriorColorRecentRecords = List.of(38, 46, 36, 35, 42, 44);
            List<Integer> exteriorColorTotalRecords = List.of(241, 241, 241, 241, 241, 241);

            List<Integer> exteriorColorChosenExpected = IntStream.range(0, exteriorColorIds.size())
                    .mapToDouble(i -> (double) exteriorColorRecentRecords.get(i) * 100 / exteriorColorTotalRecords.get(i))
                    .mapToLong(Math::round)
                    .mapToInt(operand -> (int) operand)
                    .boxed()
                    .collect(Collectors.toUnmodifiableList());

            //when
            List<Integer> exteriorColorChosen = chosenRepository.findExteriorColorChosenByExteriorColorCode(exteriorColorIds, daysAgo);

            //then
            softAssertions.assertThat(exteriorColorChosen).isEqualTo(exteriorColorChosenExpected);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<String> exteriorColorIds = List.of("AAA");
            int daysAgo = 90 + intDayDifference;

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> chosenRepository.findExteriorColorChosenByExteriorColorCode(exteriorColorIds, daysAgo);

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("모델 패키지 선택률 조회 테스트")
    class findInteriorColorChosenByInteriorColorCode {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<String> interiorColorIds = List.of("I49", "YJY");
            int daysAgo = 90 + intDayDifference;

            List<Integer> interiorColorRecentRecords = List.of(19, 19);
            List<Integer> interiorColorTotalRecords = List.of(38, 38);

            List<Integer> interiorColorChosenExpected = IntStream.range(0, interiorColorIds.size())
                    .mapToDouble(i -> (double) interiorColorRecentRecords.get(i) * 100 / interiorColorTotalRecords.get(i))
                    .mapToLong(Math::round)
                    .mapToInt(operand -> (int) operand)
                    .boxed()
                    .collect(Collectors.toUnmodifiableList());

            //when
            List<Integer> exteriorColorChosen = chosenRepository.findInteriorColorChosenByInteriorColorCode("A2B", interiorColorIds, daysAgo);

            //then
            softAssertions.assertThat(exteriorColorChosen).isEqualTo(interiorColorChosenExpected);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<String> interiorColorIds = List.of("AAA");
            int daysAgo = 90 + intDayDifference;

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> chosenRepository.findInteriorColorChosenByInteriorColorCode("A2B", interiorColorIds, daysAgo);

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }
}