package softeer.wantcar.cartalog.model.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import softeer.wantcar.cartalog.global.ServerPath;
import softeer.wantcar.cartalog.model.dto.EstimateImageDto;

import java.util.HashMap;


@JdbcTest
class ModelQueryRepositoryTest {
    SoftAssertions softAssertions;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    ModelQueryRepository modelQueryRepository;
    @Value("${env.imageServerPath}")
    private String imageServerPath = "example-url";

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        modelQueryRepository = new ModelQueryRepository(jdbcTemplate, new ServerPath());
    }

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
    @Nested
    @DisplayName("외/내장 색상을 통한 외부 옆면 및 내부 사진 조회 테스트")
    class findSideExteriorAndInteriorImageByExteriorAndInteriorColorCode {
        @Test
        @DisplayName("존재하는 식별자로 조회시 외부 옆면 및 내부 사진을 담은 dto를 반환한다")
        void returnDtoHasSideExteriorAndInteriorImage() {
            //given
            String exterior_color_code = jdbcTemplate.queryForObject("SELECT color_code FROM model_exterior_colors LIMIT 1",
                    new HashMap<>(), String.class);
            String interior_color_code = jdbcTemplate.queryForObject("SELECT code FROM model_interior_colors LIMIT 1",
                    new HashMap<>(), String.class);

            //when
            EstimateImageDto estimateImageDto =
                    modelQueryRepository.findCarSideExteriorAndInteriorImage(exterior_color_code, interior_color_code);

            //then
            softAssertions.assertThat(estimateImageDto.getSideExteriorImageUrl()).startsWith(imageServerPath + "/");
            softAssertions.assertThat(estimateImageDto.getInteriorImageUrl()).startsWith(imageServerPath + "/");
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 식별자로 조회시 null을 반환한다")
        void returnNull() {
            //given
            String exterior_color_code = jdbcTemplate.queryForObject("SELECT color_code FROM model_exterior_colors LIMIT 1",
                    new HashMap<>(), String.class);
            String interior_color_code = jdbcTemplate.queryForObject("SELECT code FROM model_interior_colors LIMIT 1",
                    new HashMap<>(), String.class);
            String notExistColorCode = "NOT-EXIST";

            //when
            EstimateImageDto wrongExteriorColorCode =
                    modelQueryRepository.findCarSideExteriorAndInteriorImage(notExistColorCode, interior_color_code);
            EstimateImageDto wrongInteriorColorCode =
                    modelQueryRepository.findCarSideExteriorAndInteriorImage(exterior_color_code, notExistColorCode);
            EstimateImageDto wrongExteriorInteriorColorCode =
                    modelQueryRepository.findCarSideExteriorAndInteriorImage(notExistColorCode, notExistColorCode);

            //then
            softAssertions.assertThat(wrongExteriorColorCode).isNull();
            softAssertions.assertThat(wrongInteriorColorCode).isNull();
            softAssertions.assertThat(wrongExteriorInteriorColorCode).isNull();
            softAssertions.assertAll();
        }
    }
}
