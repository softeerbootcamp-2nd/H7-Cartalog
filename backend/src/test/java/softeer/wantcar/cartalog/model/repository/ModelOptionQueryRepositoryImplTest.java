package softeer.wantcar.cartalog.model.repository;

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
import softeer.wantcar.cartalog.model.dto.ModelTypeDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.dto.OptionDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("모델 옵션 쿼리 Repository 테스트")
class ModelOptionQueryRepositoryImplTest {
    SoftAssertions softAssertions;
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    ModelOptionQueryRepository modelOptionQueryRepository;
    ServerPath serverPath = new ServerPath();

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        modelOptionQueryRepository = new ModelOptionQueryRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("findByModelTypeOptionsByBasicModelId 테스트")
    class findByModelId {
        @Test
        @DisplayName("존재하는 식별자로 조회시 모델 타입 옵션을 포함한 dto를 반환해야 한다.")
        void findByModelTypeOptionsByBasicModelIdWithCollectId() {
            //given
            Long trimId = 2L;
            List<String> checkTypes = List.of("파워트레인/성능", "바디타입", "구동방식");
            Map<String, List<String>> checkOptions = new HashMap<>();
            checkOptions.put("파워트레인/성능", List.of("디젤 2.2", "가솔린 3.8"));
            checkOptions.put("바디타입", List.of("7인승", "8인승"));
            checkOptions.put("구동방식", List.of("2WD", "4WD"));

            //when
            ModelTypeListResponseDto response = modelOptionQueryRepository.findByModelTypeOptionsByTrimId(trimId);

            //then
            assertThat(response).isNotNull();

            softAssertions.assertThat(response.getModelTypes().size()).isEqualTo(checkTypes.size());

            List<String> modelTypeNames = response.getModelTypes().stream()
                    .map(ModelTypeDto::getType)
                    .collect(Collectors.toList());
            softAssertions.assertThat(modelTypeNames).containsAll(checkTypes);

            for (ModelTypeDto modelType : response.getModelTypes()) {
                List<String> modelTypeOptions = modelType.getOptions().stream()
                        .map(OptionDto::getName)
                        .collect(Collectors.toList());
                softAssertions.assertThat(modelTypeOptions.size()).isEqualTo(checkOptions.get(modelType.getType()).size());
                softAssertions.assertThat(modelTypeOptions).containsAll(checkOptions.get(modelType.getType()));

                List<String> imageUrls = modelType.getOptions().stream()
                        .map(OptionDto::getImageUrl)
                        .collect(Collectors.toList());
                for (String imageUrl : imageUrls) {
                    softAssertions.assertThat(imageUrl).startsWith(serverPath.IMAGE_SERVER_PATH);
                }
            }
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("없는 식별자로 조회시 null을 반환해야 한다.")
        void findByModelTypeOptionsByBasicModelIdWithIllegalId() {
            //given
            Long basicModelId = -1L;

            //when
            ModelTypeListResponseDto response = modelOptionQueryRepository.findByModelTypeOptionsByTrimId(basicModelId);

            //then
            assertThat(response).isNull();
        }
    }

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
    @Nested
    @DisplayName("모델 타입 카테고리 조회 테스트")
    class findModelTypeCategoryByModelTypeId {
        @Test
        @DisplayName("존재하는 식별자로 조회시 모델 타입의 카테고리를 반환한다.")
        void findByModelTypeOptionsByBasicModelIdWithCollectId() {
            //given
            Long powerTrainId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name='디젤 2.2'", new HashMap<>(), Long.TYPE);
            Long wdId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name='2WD'", new HashMap<>(), Long.TYPE);
            Long bodyTypeId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name='7인승'", new HashMap<>(), Long.TYPE);
            if (powerTrainId == null || wdId == null || bodyTypeId == null) {
                throw new RuntimeException();
            }

            //when
            List<String> categories = modelOptionQueryRepository.findModelTypeCategoriesByIds(
                    List.of(powerTrainId, wdId, bodyTypeId));

            //then
            assertThat(categories).containsAll(List.of("파워트레인/성능", "구동방식", "바디타입"));
        }

        @Test
        @DisplayName("존재하지 않는 식별자로 조회시 null을 반환해야 한다.")
        void findByModelTypeOptionsByBasicModelIdWithIllegalId() {
            //given
            //when
            List<String> categories = modelOptionQueryRepository.findModelTypeCategoriesByIds(
                    List.of(-1L));

            //then
            assertThat(categories).isNull();
        }
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    @Nested
    @DisplayName("findHashTagFromOptionsByOptionIds 테스트")
    class findHashTagFromOptionsByOptionIdsTest {
        List<Long> optionIds;

        @BeforeEach
        void setUp() {
            optionIds = jdbcTemplate.queryForList("SELECT mo.id " +
                                                  "FROM model_option_hash_tags AS moht " +
                                                  "JOIN model_options AS mo ON mo.id=moht.model_option_id ",
                    new HashMap<>(), Long.TYPE);
        }

        @Test
        @DisplayName("옵션 식별자에 해당하는 해시 태그 목록을 반환한다")
        void returnHashTagList() {
            //given
            //when
            List<String> hashTags = modelOptionQueryRepository.findHashTagFromOptionsByOptionIds(optionIds);

            //then
            softAssertions.assertThat(hashTags.isEmpty()).isFalse();
            softAssertions.assertThat(hashTags.size()).isNotEqualTo(hashTags.stream().distinct().count());
            softAssertions.assertAll();
        }
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    @Nested
    @DisplayName("findHashTagFromPackagesByPackageIds 테스트")
    class findHashTagFromPackagesByPackageIdsTest {
        List<Long> packageIds;

        @BeforeEach
        void setUp() {
            packageIds = jdbcTemplate.queryForList("SELECT mp.id " +
                                                   "FROM model_package_hash_tags AS mpht " +
                                                   "JOIN model_packages AS mp ON mp.id=mpht.model_package_id ",
                    new HashMap<>(), Long.TYPE);
        }

        @Test
        @DisplayName("패키지 식별자에 해당하는 해시 태그 목록을 반환한다")
        void returnHashTagList() {
            //given
            //when
            List<String> hashTags = modelOptionQueryRepository.findHashTagFromPackagesByPackageIds(packageIds);

            //then
            softAssertions.assertThat(hashTags.isEmpty()).isFalse();
            softAssertions.assertThat(hashTags.size()).isNotEqualTo(hashTags.stream().distinct().count());
            softAssertions.assertAll();
        }
    }
}
