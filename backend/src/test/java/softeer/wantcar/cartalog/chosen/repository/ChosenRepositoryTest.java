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
            List<Long> modelTypeIds = List.of(1L, 2L, 3L, 4L, 5L, 6L);
            int daysAgo = 90;

            List<Integer> modelTypeRecentRecords = List.of(126, 115, 126, 115, 126, 115);
            List<Integer> modelTypeTotalRecords = List.of(499, 501, 528, 472, 506, 494);

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
            int daysAgo = 90;

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> chosenRepository.findModelTypeChosenByOptionId(modelTypeIds, daysAgo);

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("모델 타입 옵션 선택률 조회 테스트")
    class findOptionChosenByOptionIdTest {
        @Test
        @DisplayName("선택률을 가져와야 한다.")
        void success() {
            //given
            List<Long> modelTypeIds = List.of(97L, 98L, 99L, 100L, 101L, 102L, 103L, 104L, 105L, 106L, 107L);
            int daysAgo = 90;

            List<Integer> modelTypeRecentRecords = List.of(57, 34, 42, 53, 41, 45, 39, 40, 48, 42, 40);
            List<Integer> modelTypeTotalRecords = List.of(241, 241, 241, 126, 241, 241, 241, 241, 241, 241, 241);

            List<Integer> modelTypeChosenExpected = IntStream.range(0, modelTypeIds.size())
                    .mapToDouble(i -> (double) modelTypeRecentRecords.get(i) * 100 / modelTypeTotalRecords.get(i))
                    .mapToLong(Math::round)
                    .mapToInt(operand -> (int) operand)
                    .boxed()
                    .collect(Collectors.toUnmodifiableList());

            //when
            List<Integer> modelTypeChosen = chosenRepository.findOptionChosenByOptionId(modelTypeIds, daysAgo);

            //then
            softAssertions.assertThat(modelTypeChosen).isEqualTo(modelTypeChosenExpected);
        }

        @Test
        @DisplayName("잘못된 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            List<Long> modelTypeIds = List.of(10L);
            int daysAgo = 90;

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> chosenRepository.findOptionChosenByOptionId(modelTypeIds, daysAgo);

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }
}