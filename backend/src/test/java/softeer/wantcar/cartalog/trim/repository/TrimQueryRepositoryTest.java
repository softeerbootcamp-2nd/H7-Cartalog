package softeer.wantcar.cartalog.trim.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.entity.model.BasicModel;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.model.repository.ModelQueryRepository;
import softeer.wantcar.cartalog.trim.dto.DetailTrimInfoDto;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Sql({"classpath:schema.sql"})
class TrimQueryRepositoryTest {
    @Value("${env.imageServerPath}")
    String imageServerPath = "example-url";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    ModelQueryRepository modelQueryRepository;
    TrimQueryRepository trimQueryRepository;
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        modelQueryRepository = new ModelQueryRepository(jdbcTemplate);
        trimQueryRepository = new TrimQueryRepositoryImpl(jdbcTemplate);
        softAssertions = new SoftAssertions();
    }


    @Nested
    @DisplayName("트림 목록 조회 테스트")
    class getTrimListTest {
        @Test
        @DisplayName("modelId가 존재하면 modelId 맞는 트림 목록을 반환한다")
        void returnTrimListMatchedModelIdWhenModelIdExist() {
            //given
            BasicModel basicModel = modelQueryRepository.findBasicModelByName("팰리세이드");

            //when
            TrimListResponseDto trimListResponseDto = trimQueryRepository.findTrimsByBasicModelId(basicModel.getId());

            //then
            softAssertions.assertThat(trimListResponseDto).isNotNull();
            softAssertions.assertThat(trimListResponseDto.getModelName()).isEqualTo("팰리세이드");

            List<TrimListResponseDto.TrimDto> trimDtoList = trimListResponseDto.getTrims();
            softAssertions.assertThat(trimDtoList).isNotNull();
            softAssertions.assertThat(trimDtoList.size()).isEqualTo(4);

            verifyPalisadeTrimDto(trimDtoList.stream().filter(t -> t.getName().equals("Exclusive")).findFirst().orElseThrow(),
                    "Exclusive", "기본에 충실한 팰리세이드",
                    41980000, 50880000, "I50");

            verifyPalisadeTrimDto(trimDtoList.stream().filter(t -> t.getName().equals("Le Blanc")).findFirst().orElseThrow(),
                    "Le Blanc", "합리적인 조합의 절정",
                    38960000, 56770000, "I49");

            verifyPalisadeTrimDto(trimDtoList.stream().filter(t -> t.getName().equals("Prestige")).findFirst().orElseThrow(),
                    "Prestige", "프리미엄한 차량경험",
                    46240000, 63160000, "WDN");

            verifyPalisadeTrimDto(trimDtoList.stream().filter(t -> t.getName().equals("Calligraphy")).findFirst().orElseThrow(),
                    "Calligraphy", "모두가 선망하는 이동경험",
                    51060000, 64720000, "NBX");
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("modelId에 해당하는 모델이 존재하지 않는다면 null을 반환한다")
        void returnNullWhenModelIdNotMatched() {
            //given
            //when
            TrimListResponseDto trimListResponseDto = trimQueryRepository.findTrimsByBasicModelId(-1L);

            //then
            assertThat(trimListResponseDto).isNull();
        }
    }

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
    @Nested
    @DisplayName("세부 트림 조회 테스트")
    class getDetailTrimTest {
        @Test
        @DisplayName("적절한 트림 식별자와 모델 타입 식별자들을 전달할 경우 이에 맞는 세부 트림 정보를 반환한다.")
        void returnDetailTrimInfos() {
            //given
            Long trimId = jdbcTemplate.queryForObject("SELECT id FROM trims WHERE name = 'Le Blanc' ;", new HashMap<>(), Long.TYPE);
            Long powerTrainId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name = '디젤 2.2' ;", new HashMap<>(), Long.TYPE);
            Long wdId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name = '2WD' ;", new HashMap<>(), Long.TYPE);
            Long bodyTypeId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name = '7인승' ;", new HashMap<>(), Long.TYPE);
            if (trimId == null || powerTrainId == null || wdId == null || bodyTypeId == null) {
                throw new RuntimeException();
            }

            //when
            DetailTrimInfoDto detailTrimInfoDto = trimQueryRepository.findDetailTrimInfoByTrimIdAndModelTypeIds(
                    trimId, List.of(powerTrainId, wdId, bodyTypeId));

            //then
            softAssertions.assertThat(detailTrimInfoDto.getDisplacement()).isEqualTo(2199.0);
            softAssertions.assertThat(detailTrimInfoDto.getFuelEfficiency()).isEqualTo(12.16);
            softAssertions.assertAll();
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("존재하지 않는 식별자일 경우 null을 반환해야 한다")
        void returnNullWhenIdIsNotExist() {
            //given
            Long trimId = jdbcTemplate.queryForObject("SELECT id FROM trims WHERE name = 'Le Blanc' ;", new HashMap<>(), Long.TYPE);
            Long powerTrainId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name = '디젤 2.2' ;", new HashMap<>(), Long.TYPE);
            Long wdId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name = '2WD' ;", new HashMap<>(), Long.TYPE);
            Long bodyTypeId = -1L;

            //when
            DetailTrimInfoDto detailTrimInfoDto = trimQueryRepository.findDetailTrimInfoByTrimIdAndModelTypeIds(
                    trimId, List.of(powerTrainId, wdId, bodyTypeId));

            //then
            assertThat(detailTrimInfoDto).isNull();
        }

        @Test
        @DisplayName("동일한 유형의 모델 타입 식별자를 전달할 경우 null을 반환한다")
        void returnNullWhenModelTypeIdOverlapped() {
            //given
            Long trimId = jdbcTemplate.queryForObject("SELECT id FROM trims WHERE name = 'Le Blanc' ;", new HashMap<>(), Long.TYPE);
            Long powerTrainId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name = '디젤 2.2' ;", new HashMap<>(), Long.TYPE);
            Long bodyTypeId = jdbcTemplate.queryForObject("SELECT id FROM model_options WHERE name = '7인승' ;", new HashMap<>(), Long.TYPE);
            assert trimId != null;
            assert powerTrainId != null;
            assert bodyTypeId != null;

            //when
            DetailTrimInfoDto detailTrimInfoDto = trimQueryRepository.findDetailTrimInfoByTrimIdAndModelTypeIds(
                    trimId, List.of(powerTrainId, powerTrainId, bodyTypeId));

            //then
            assertThat(detailTrimInfoDto).isNull();
        }
    }

    private void verifyPalisadeTrimDto(TrimListResponseDto.TrimDto exclusive,
                                       String modelName,
                                       String description,
                                       int minPrice,
                                       int maxPrice,
                                       String interiorColorId) {
        softAssertions.assertThat(exclusive.getName()).isEqualTo(modelName);
        softAssertions.assertThat(exclusive.getDescription()).isEqualTo(description);
        softAssertions.assertThat(exclusive.getMinPrice()).isEqualTo(minPrice);
        softAssertions.assertThat(exclusive.getMaxPrice()).isEqualTo(maxPrice);
        softAssertions.assertThat(exclusive.getExteriorImageUrl()).startsWith(imageServerPath + "/");
        softAssertions.assertThat(exclusive.getInteriorImageUrl()).startsWith(imageServerPath + "/");
        softAssertions.assertThat(exclusive.getWheelImageUrl()).startsWith(imageServerPath + "/");

        List<HMGDataDto> hmgData = exclusive.getHmgData();
        softAssertions.assertThat(hmgData).isNotNull();
        softAssertions.assertThat(hmgData.size()).isLessThanOrEqualTo(3);
        for (int idx = 0; idx < hmgData.size() - 1; idx++) {
            softAssertions.assertThat(hmgData.get(idx))
                    .isNotEqualTo(hmgData.get(idx + 1));
        }

        TrimListResponseDto.DefaultTrimInfoDto defaultInfo = exclusive.getDefaultInfo();
        softAssertions.assertThat(defaultInfo).isNotNull();
        softAssertions.assertThat(defaultInfo.getExteriorColorId()).isEqualTo("A2B");
        softAssertions.assertThat(defaultInfo.getInteriorColorId()).isEqualTo(interiorColorId);
        List<TrimListResponseDto.ModelTypeDto> defaultModeTypes = defaultInfo.getModelTypes();
        softAssertions.assertThat(defaultModeTypes).isNotNull();
        softAssertions.assertThat(defaultModeTypes.size()).isEqualTo(3);

        TrimListResponseDto.ModelTypeDto powerTrain = defaultModeTypes.stream()
                .filter(m -> m.getType().equals("파워트레인/성능"))
                .findFirst()
                .orElseThrow();
        softAssertions.assertThat(powerTrain).isNotNull();
        softAssertions.assertThat(powerTrain.getType()).isEqualTo("파워트레인/성능");
        TrimListResponseDto.OptionDto powerTrainOption = powerTrain.getOption();
        softAssertions.assertThat(powerTrainOption).isNotNull();
        softAssertions.assertThat(powerTrainOption.getName()).isEqualTo("디젤 2.2");
        softAssertions.assertThat(powerTrainOption.getPrice()).isEqualTo(1480000);

        TrimListResponseDto.ModelTypeDto wheelDrive = defaultModeTypes.stream()
                .filter(m -> m.getType().equals("구동방식"))
                .findFirst()
                .orElseThrow();
        softAssertions.assertThat(wheelDrive).isNotNull();
        softAssertions.assertThat(wheelDrive.getType()).isEqualTo("구동방식");
        TrimListResponseDto.OptionDto wheelDriveOption = wheelDrive.getOption();
        softAssertions.assertThat(wheelDriveOption).isNotNull();
        softAssertions.assertThat(wheelDriveOption.getName()).isEqualTo("2WD");
        softAssertions.assertThat(wheelDriveOption.getPrice()).isEqualTo(0);

        TrimListResponseDto.ModelTypeDto bodyType = defaultModeTypes.stream()
                .filter(m -> m.getType().equals("바디타입"))
                .findFirst()
                .orElseThrow();
        softAssertions.assertThat(bodyType).isNotNull();
        softAssertions.assertThat(bodyType.getType()).isEqualTo("바디타입");
        TrimListResponseDto.OptionDto bodyTypeOption = bodyType.getOption();
        softAssertions.assertThat(bodyTypeOption).isNotNull();
        softAssertions.assertThat(bodyTypeOption.getName()).isEqualTo("7인승");
        softAssertions.assertThat(bodyTypeOption.getPrice()).isEqualTo(0);
    }
}
