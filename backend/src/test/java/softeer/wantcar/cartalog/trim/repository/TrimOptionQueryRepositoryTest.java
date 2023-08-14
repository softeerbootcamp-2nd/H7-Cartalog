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
import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;

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
                    "FROM detail_trims AS dt " +
                    "JOIN trims AS t ON t.id=dt.trim_id " +
                    "WHERE t.name= 'Le Blanc' " +
                    "LIMIT 1",
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

    @Nested
    @DisplayName("모델 옵션 상세 정보 보기 테스트")
    class findModelOptionDetailByOptionId {
        @Test
        @DisplayName("존재하는 모델 식별자를 전달하면 모델 상세 정보를 반환한다.")
        void success() {
            //given
            Long palisadeLeBlancDiesel2WD7SeatDieselDetailTrimOptionId = jdbcTemplate.queryForObject("SELECT detail_trim_options.id " +
                            "FROM basic_models " +
                            "       INNER JOIN model_options AS mo1 " +
                            "               ON basic_models.id = mo1.model_id " +
                            "       INNER JOIN detail_trim_options " +
                            "               ON mo1.id = detail_trim_options.model_option_id " +
                            "       INNER JOIN detail_trims " +
                            "               ON detail_trim_options.detail_trim_id = detail_trims.id " +
                            "       INNER JOIN trims " +
                            "               ON detail_trims.trim_id = trims.id " +
                            "       INNER JOIN detail_models " +
                            "               ON detail_trims.detail_model_id = detail_models.id " +
                            "       INNER JOIN detail_model_decision_options " +
                            "               ON detail_models.id = detail_model_decision_options.detail_model_id " +
                            "       INNER JOIN model_options AS mo2 " +
                            "               ON detail_model_decision_options.model_option_id = mo2.id " +
                            "WHERE  basic_models.name = '팰리세이드' " +
                            "       AND mo1.name = '디젤 2.2' " +
                            "       AND trims.name = 'Le Blanc' " +
                            "       AND mo2.name IN ( '디젤 2.2', '2WD', '7인승' ) " +
                            "GROUP  BY detail_trim_options.id " +
                            "HAVING Count(detail_trim_options.id) = 3; ",
                    new HashMap<>(),
                    Long.TYPE
            );

            //when
            TrimOptionDetailResponseDto palisadeDieselOptionDetail =
                    trimOptionQueryRepository.findTrimOptionDetailByDetailTrimOptionId(palisadeLeBlancDiesel2WD7SeatDieselDetailTrimOptionId);

            //then
            softAssertions.assertThat(palisadeDieselOptionDetail.isPackage()).isEqualTo(false);
            softAssertions.assertThat(palisadeDieselOptionDetail.getName()).isEqualTo("디젤 2.2");
            softAssertions.assertThat(palisadeDieselOptionDetail.getHashTags().size()).isEqualTo(0);
            softAssertions.assertThat(palisadeDieselOptionDetail.getHmgData().size()).isEqualTo(2);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 옵션 식별자라면 null을 반환한다.")
        void failByNonExistenceOptionId() {
            //given
            //when
            TrimOptionDetailResponseDto result =
                    trimOptionQueryRepository.findTrimOptionDetailByDetailTrimOptionId(-1L);

            //then
            assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("모델 패키지 상세 정보 보기 테스트")
    class findTrimPackageDetailByPackageId {
        @Test
        @DisplayName("성공")
        void success() {
            //given
            Long palisadeLeBlancDiesel2WD7SeatCompose2PackageId = jdbcTemplate.queryForObject("SELECT detail_trim_packages.id " +
                            "FROM   detail_trim_packages " +
                            "       INNER JOIN detail_trims " +
                            "               ON detail_trim_packages.detail_trim_id = detail_trims.id " +
                            "       INNER JOIN detail_models " +
                            "               ON detail_trims.detail_model_id = detail_models.id " +
                            "       INNER JOIN detail_model_decision_options " +
                            "               ON detail_models.id = detail_model_decision_options.detail_model_id " +
                            "       INNER JOIN model_options " +
                            "               ON detail_model_decision_options.model_option_id = model_options.id " +
                            "       INNER JOIN basic_models " +
                            "               ON basic_models.id = detail_models.basic_model_id " +
                            "       INNER JOIN trims " +
                            "               ON detail_trims.trim_id = trims.id " +
                            "WHERE  detail_trim_packages.name = '컴포트 Ⅱ' " +
                            "       AND basic_models.name = '팰리세이드' " +
                            "       AND trims.name = 'Le Blanc' " +
                            "       AND model_options.name IN ( '디젤 2.2', '2WD', '7인승' ) " +
                            "GROUP  BY detail_trim_packages.id " +
                            "HAVING Count(detail_trim_packages.id) = 3",
                    new HashMap<>(),
                    Long.TYPE
            );

            //when
            TrimPackageDetailResponseDto compose2PackageDetail = trimOptionQueryRepository.findTrimPackageDetailByPackageId(palisadeLeBlancDiesel2WD7SeatCompose2PackageId);

            //then
            softAssertions.assertThat(compose2PackageDetail.isPackage()).isEqualTo(true);
            softAssertions.assertThat(compose2PackageDetail.getName()).isEqualTo("컴포트 Ⅱ");
            softAssertions.assertThat(compose2PackageDetail.getHashTags().size()).isEqualTo(5);
            softAssertions.assertThat(compose2PackageDetail.getOptions().size()).isEqualTo(6);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 옵션 식별자라면 null을 반환한다.")
        void failByNonExistencePackageId() {
            //given
            //when
            List<TrimOptionQueryRepository.TrimOptionInfo> result =
                    trimOptionQueryRepository.findPackagesByDetailTrimId(-1L);

            //then
            assertThat(result).isNull();
        }
    }
}
