package softeer.wantcar.cartalog.trim.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.wantcar.cartalog.entity.model.BasicModel;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.model.repository.ModelQueryRepository;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Sql({"classpath:schema.sql"})
class TrimQueryRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

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

        List<HMGDataDto> hmgData = exclusive.getHmgData();
        softAssertions.assertThat(hmgData).isNotNull();
        softAssertions.assertThat(hmgData.size()).isLessThanOrEqualTo(3);

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
