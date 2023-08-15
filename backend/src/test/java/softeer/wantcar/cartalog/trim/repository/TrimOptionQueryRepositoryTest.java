package softeer.wantcar.cartalog.trim.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.global.utils.CompareUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    class findModelOptionDetailByOptionIdTest {
        String palisadeLeBlancDiesel2WD7SeatSQL = "SELECT detail_trim_options.id " +
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
                "       AND mo1.name = :optionName " +
                "       AND trims.name = 'Le Blanc' " +
                "       AND mo2.name IN ( '디젤 2.2', '2WD', '7인승' ) " +
                "GROUP  BY detail_trim_options.id " +
                "HAVING Count(detail_trim_options.id) = 3";

        @Test
        @DisplayName("존재하는 모델 옵션 식별자를 전달하면 모델 옵션 정보를 반환한다.")
        void findModelOptionInfoByOptionId() {
            //given
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("optionName", "디젤 2.2");
            Long dieselDetailTrimOptionId = jdbcTemplate.queryForObject(palisadeLeBlancDiesel2WD7SeatSQL,
                    parameters,
                    Long.class
            );
            Long modelOptionId = trimOptionQueryRepository.findModelOptionIdByDetailTrimOptionId(dieselDetailTrimOptionId);

            //when
            TrimOptionQueryRepository.ModelOptionInfo modelOption = trimOptionQueryRepository.findModelOptionInfoByOptionId(modelOptionId);

            //then
            softAssertions.assertThat(modelOption.getName()).isEqualTo("디젤 2.2");
            softAssertions.assertThat(modelOption.getImageUrl().startsWith(serverPath.IMAGE_SERVER_PATH)).isTrue();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않은 옵션 식별자를 전달하면 null을 반환해야 한다.")
        void findModelOptionInfoByNonExistenceOptionId() {
            //given
            //when
            TrimOptionQueryRepository.ModelOptionInfo modelOption = trimOptionQueryRepository.findModelOptionInfoByOptionId(-1L);

            //then
            assertThat(modelOption).isNull();
        }

        @Test
        @DisplayName("존재하는 모델 식별자를 전달하면 모델 상세 정보를 반환한다.")
        void findHashTagsByOptionId() {
            //given
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("optionName", "빌트인 캠(보조배터리 포함)");
            Long builtInCampModelOptionId = jdbcTemplate.queryForObject(palisadeLeBlancDiesel2WD7SeatSQL,
                    parameters,
                    Long.class
            );
            Long modelOptionId =
                    trimOptionQueryRepository.findModelOptionIdByDetailTrimOptionId(builtInCampModelOptionId);
            List<String> expected = List.of("장거리 운전", "안전", "주차/출차");

            //when
            List<String> hashTags = trimOptionQueryRepository.findHashTagsByOptionId(modelOptionId);

            //then
            assertThat(CompareUtils.equalAndAllContain(expected, hashTags)).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 옵션 식별자라면 빈 리스트를 반환한다.")
        void findHashTagsByNonExistenceOptionId() {
            //given
            //when
            List<String> hashTags = trimOptionQueryRepository.findHashTagsByOptionId(-1L);

            //then
            assertThat(hashTags.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("존재하는 모델 식별자를 전달하면 해당 모델의 hmg데이터를 반환한다.")
        void findHMGDataInfoListByOptionId() {
            //given
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("optionName", "디젤 2.2");
            Long dieselDetailTrimOptionId = jdbcTemplate.queryForObject(palisadeLeBlancDiesel2WD7SeatSQL,
                    parameters,
                    Long.class
            );
            Long modelOptionId =
                    trimOptionQueryRepository.findModelOptionIdByDetailTrimOptionId(dieselDetailTrimOptionId);
            List<String> expected = List.of("최고출력", "최대토크");

            //when
            List<TrimOptionQueryRepository.HMGDataInfo> hmgDataInfos = trimOptionQueryRepository.findHMGDataInfoListByOptionId(modelOptionId);

            //then
            List<String> hmgDataNames = hmgDataInfos.stream()
                    .map(TrimOptionQueryRepository.HMGDataInfo::getName)
                    .collect(Collectors.toUnmodifiableList());
            assertThat(CompareUtils.equalAndAllContain(expected, hmgDataNames)).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 옵션 식별자라면 빈 리스트를 반환한다.")
        void findHMGDataInfoListByNonExistenceOptionId() {
            //given
            //when
            List<TrimOptionQueryRepository.HMGDataInfo> hmgDataInfos = trimOptionQueryRepository.findHMGDataInfoListByOptionId(-1L);

            //then
            assertThat(hmgDataInfos.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("모델 패키지 상세 정보 보기 테스트")
    class findTrimPackageDetailByPackageId {
        String palisadeLeBlancDiesel2WD7SeatSQL = "SELECT detail_trim_packages.id " +
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
                "WHERE  detail_trim_packages.name = :packageName" +
                "       AND basic_models.name = '팰리세이드' " +
                "       AND trims.name = 'Le Blanc' " +
                "       AND model_options.name IN ( '디젤 2.2', '2WD', '7인승' ) " +
                "GROUP  BY detail_trim_packages.id " +
                "HAVING Count(detail_trim_packages.id) = 3";

        @Test
        @DisplayName("존재하는 패키지 식별자를 전달하면 패키지 정보를 반환한다.")
        void findDetailTrimPackageInfoByPackageId() {
            //given
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("packageName", "컴포트 Ⅱ");
            Long compose2PackageId = jdbcTemplate.queryForObject(palisadeLeBlancDiesel2WD7SeatSQL,
                    parameters,
                    Long.TYPE
            );

            //when
            TrimOptionQueryRepository.DetailTrimPackageInfo detailTrimPackageInfo =
                    trimOptionQueryRepository.findDetailTrimPackageInfoByPackageId(compose2PackageId);

            //then
            softAssertions.assertThat(detailTrimPackageInfo.getName()).isEqualTo("컴포트 Ⅱ");
            softAssertions.assertThat(detailTrimPackageInfo.getImageUrl().startsWith(serverPath.IMAGE_SERVER_PATH)).isTrue();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 패키지 식별자라면 null을 반환한다.")
        void findDetailTrimPackageInfoByNonExistencePackageId() {
            //given
            //when
            TrimOptionQueryRepository.DetailTrimPackageInfo detailTrimPackageInfo =
                    trimOptionQueryRepository.findDetailTrimPackageInfoByPackageId(-1L);

            //then
            assertThat(detailTrimPackageInfo).isNull();
        }

        @Test
        @DisplayName("존재하는 패키지 식별자를 전달하면 패키지 정보를 반환한다.")
        void findPackageHashTagByPackageId() {
            //given
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("packageName", "컴포트 Ⅱ");
            Long compose2PackageId = jdbcTemplate.queryForObject(palisadeLeBlancDiesel2WD7SeatSQL,
                    parameters,
                    Long.TYPE
            );
            List<String> expect = List.of("레저", "스타일", "안전", "자녀", "반려동물");

            //when
            List<String> packageHashTags = trimOptionQueryRepository.findPackageHashTagByPackageId(compose2PackageId);

            //then
            assertThat(CompareUtils.equalAndAllContain(expect, packageHashTags)).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 패키지 식별자를 전달하면 빈 리스트를 반환한다.")
        void findPackageHashTagByNonExistencePackageId() {
            //given
            //when
            List<String> packageHashTags = trimOptionQueryRepository.findPackageHashTagByPackageId(-1L);

            //then
            assertThat(packageHashTags.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("존재하는 패키지 식별자를 전달하면 패키지에 포함된 모델 식별자들을 반환한다.")
        void findModelOptionIdsByPackageId() {
            //given
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("packageName", "컴포트 Ⅱ");
            Long compose2PackageId = jdbcTemplate.queryForObject(palisadeLeBlancDiesel2WD7SeatSQL,
                    parameters,
                    Long.TYPE
            );
            List<String> expect = List.of("후석 승객알림", "메탈 리어범퍼스텝", "메탈 도어스커프", "3열 파워폴딩시트", "3열 열선시트", "헤드업 디스플레이");

            //when
            List<Long> modelOptionIds = trimOptionQueryRepository.findModelOptionIdsByPackageId(compose2PackageId);

            //then
            List<TrimOptionQueryRepository.ModelOptionInfo> modelOptionInfos = modelOptionIds.stream()
                    .map(trimOptionQueryRepository::findModelOptionInfoByOptionId)
                    .collect(Collectors.toUnmodifiableList());
            List<String> modelOptionNames = modelOptionInfos.stream()
                    .map(TrimOptionQueryRepository.ModelOptionInfo::getName)
                    .collect(Collectors.toUnmodifiableList());

            assertThat(CompareUtils.equalAndAllContain(expect, modelOptionNames)).isTrue();
        }

        @Test
        @DisplayName("존재하지 않은 패키지 식별자를 전달하면 빈 리스트을 반환한다.")
        void findModelOptionIdsByNonexistentPackageId() {
            //given
            //when
            List<Long> modelOptionIds = trimOptionQueryRepository.findModelOptionIdsByPackageId(-1L);

            //then
            assertThat(modelOptionIds.isEmpty()).isTrue();
        }
    }
}
