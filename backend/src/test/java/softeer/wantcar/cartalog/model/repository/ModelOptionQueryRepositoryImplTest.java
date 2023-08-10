package softeer.wantcar.cartalog.model.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
@DisplayName("모델 옵션 쿼리 Repository 테스트")
class ModelOptionQueryRepositoryImplTest {
    SoftAssertions softAssertions;
    @Autowired
    JdbcTemplate jdbcTemplate;
    ModelOptionQueryRepository modelOptionQueryRepository;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        modelOptionQueryRepository = new ModelOptionQueryRepositoryImpl(jdbcTemplate);
    }

    @Nested
    @DisplayName("findByModelId 테스트")
    class findByModelId {
        @Test
        @DisplayName("존재하는 식별자로 조회시 모델 타입 옵션을 포함한 dto를 반환해야 한다.")
        void findByModelTypeOptionsByIdWithCollectId() {
            //given
            Long modelId = 1L;
            List<String> checkTypes = List.of("파워트레인/성능", "바디타입", "구동방식");
            Map<String, List<String>> checkOptions = new HashMap<>();
            checkOptions.put("파워트레인/성능", List.of("디젤 2.2", "가솔린 3.8"));
            checkOptions.put("바디타입", List.of("7인승", "8인승"));
            checkOptions.put("구동방식", List.of("2WD", "4WD"));

            //when
            ModelTypeListResponseDto response = modelOptionQueryRepository.findByModelTypeOptionsById(modelId);

            //then
            assertThat(response).isNotNull();
            softAssertions.assertThat(response.modelTypeSize()).isEqualTo(3);
            softAssertions.assertThat(response.equalAndAllContainTypes(checkTypes)).isTrue();
            softAssertions.assertThat(response.hasOptions(checkOptions)).isTrue();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("없는 식별자로 조회시 빈 dto를 반환해야 한다.")
        void findByModelTypeOptionsByIdWithIllegalId() {
            //given
            //when
            ModelTypeListResponseDto response = modelOptionQueryRepository.findByModelTypeOptionsById(-1L);

            //then
            assertThat(response).isNotNull();
            assertThat(response.modelTypeSize()).isEqualTo(0);
        }
    }
}
