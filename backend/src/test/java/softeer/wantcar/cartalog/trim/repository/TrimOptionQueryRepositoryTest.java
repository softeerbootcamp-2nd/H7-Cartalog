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

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("트림 옵션 Repository 테스트")
class TrimOptionQueryRepositoryTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    TrimOptionQueryRepository trimOptionQueryRepository;
    ServerPath serverPath = new ServerPath();
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        trimOptionQueryRepository = new TrimOptionQueryRepositoryImpl(serverPath, jdbcTemplate);
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
            List<TrimOptionQueryRepository.TrimOptionInfo> trimOptions
                    = trimOptionQueryRepository.findOptionsByDetailTrimId(leblancDetailTrimId);

            softAssertions.assertThat(trimOptions.isEmpty()).isFalse();
            for (TrimOptionQueryRepository.TrimOptionInfo trimOption : trimOptions) {
                softAssertions.assertThat(trimOption.getId()).startsWith("O");
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
            List<TrimOptionQueryRepository.TrimOptionInfo> trimOptions
                    = trimOptionQueryRepository.findOptionsByDetailTrimId(notExistDetailTrimId);

            assertThat(trimOptions).isNull();
        }
    }

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
    @Nested
    @DisplayName("트림 패키지 목록 조회 테스트")
    class getTrimPackageListTest {
        @Test
        @DisplayName("존재하는 세부 트림 식별자라면 트림 패키지 목록을 리스트로 반환한다")
        void returnTrimPackageListWhenDetailTrimIdExist() {
            //given
            Long leblancDetailTrimId = jdbcTemplate.queryForObject(
                    "SELECT " +
                    "   dt.id " +
                    "FROM detail_trims AS dt " +
                    "JOIN trims AS t ON t.id=dt.trim_id " +
                    "WHERE t.name= 'Le Blanc' " +
                    "LIMIT 1",
                    new HashMap<>(), Long.TYPE);

            //when
            List<TrimOptionQueryRepository.TrimOptionInfo> trimPackages
                    = trimOptionQueryRepository.findPackagesByDetailTrimId(leblancDetailTrimId);

            softAssertions.assertThat(trimPackages.isEmpty()).isFalse();
            for (TrimOptionQueryRepository.TrimOptionInfo trimPackage : trimPackages) {
                softAssertions.assertThat(trimPackage.getId()).startsWith("P");
                softAssertions.assertThat(trimPackage.getImageUrl()).startsWith(serverPath.IMAGE_SERVER_PATH);
            }
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 세부 트림 식별자라면 null을 반환한다")
        void returnNullWhenDetailTrimIdNotExist() {
            //given
            Long notExistDetailTrimId = -1L;

            //when
            List<TrimOptionQueryRepository.TrimOptionInfo> trimPackages
                    = trimOptionQueryRepository.findPackagesByDetailTrimId(notExistDetailTrimId);

            assertThat(trimPackages).isNull();
        }
    }

    @Nested
    @DisplayName("복수 선택 가능 카테고리 목록 조회 테스트")
    class getMultipleSelectableCategoryListTest {
        @Test
        @DisplayName("복수 선택 가능 카테고리 목록을 반환한다")
        void returnMultipleSelectableCategoryList() {
            //given
            //when
            List<String> multipleSelectableCategories = trimOptionQueryRepository.findMultipleSelectableCategories();

            //then
            assertThat(multipleSelectableCategories).containsAll(List.of("상세품목", "악세사리"));
        }
    }
}
