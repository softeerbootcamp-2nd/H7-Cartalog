package softeer.wantcar.cartalog.model.service;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeOptionDto;
import softeer.wantcar.cartalog.model.dto.OptionDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.model.repository.dto.ModelTypeDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SoftAssertionsExtension.class)
class ModelOptionServiceTest {
    @InjectSoftAssertions
    SoftAssertions softAssertions;
    ModelOptionQueryRepository modelOptionQueryRepository;
    ChosenRepository chosenRepository;
    ModelOptionService modelOptionService;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        modelOptionQueryRepository = mock(ModelOptionQueryRepository.class);
        chosenRepository = mock(ChosenRepository.class);
        modelOptionService = new ModelOptionServiceImpl(modelOptionQueryRepository, chosenRepository);
    }

    @Nested
    @DisplayName("모델 타입 리스트 반환 테스트")
    class findModelTypeListByTrimIdTest {
        @Test
        @DisplayName("모델 타입을 선택률과 함께 반환해야 한다.")
        void success() {
            //given
            ModelTypeDto modelType1 = ModelTypeDto.builder().modelOptionId(1L).childCategory("category1").build();
            ModelTypeDto modelType4 = ModelTypeDto.builder().modelOptionId(4L).childCategory("category1").build();
            ModelTypeDto modelType2 = ModelTypeDto.builder().modelOptionId(2L).childCategory("category2").build();
            ModelTypeDto modelType3 = ModelTypeDto.builder().modelOptionId(3L).childCategory("category3").build();
            List<ModelTypeDto> modelTypes = List.of(modelType1, modelType2, modelType3, modelType4);
            when(modelOptionQueryRepository.findModelTypeByTrimId(anyLong())).thenReturn(modelTypes);
            List<String> modelOptionIds = List.of(
                    "O" + modelType1.getModelOptionId(),
                    "O" + modelType2.getModelOptionId(),
                    "O" + modelType3.getModelOptionId(),
                    "O" + modelType4.getModelOptionId());

            List<Integer> chosen = List.of(10, 20, 30, 40);
            when(chosenRepository.findModelTypeChosenByOptionId(modelOptionIds)).thenReturn(chosen);

            //when
            ModelTypeListResponseDto modelTypeList = modelOptionService.findModelTypeListByTrimId(anyLong());

            //then
            List<String> collect = modelTypeList.getModelTypes().stream()
                    .map(ModelTypeOptionDto::getType)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(collect.containsAll(List.of("category1", "category2", "category3"))).isTrue();
            List<OptionDto> options = modelTypeList.getModelTypes().stream()
                    .filter(modelTypeDto -> modelTypeDto.getType().equals("category1"))
                    .findAny()
                    .orElseThrow()
                    .getOptions();

            softAssertions.assertThat(options.size()).isEqualTo(2);

            List<Long> optionIds = options.stream()
                    .map(OptionDto::getId)
                    .collect(Collectors.toUnmodifiableList());
            List<Integer> optionChosen = options.stream()
                    .map(OptionDto::getChosen)
                    .collect(Collectors.toUnmodifiableList());

            softAssertions.assertThat(optionIds.containsAll(List.of(1L, 4L))).isTrue();
            softAssertions.assertThat(optionChosen.containsAll(List.of(10, 40))).isTrue();
        }

        @Test
        @DisplayName("없는 트림 식별자를 전달하면 오류를 발생해야 한다.")
        void failure() {
            //given
            when(modelOptionQueryRepository.findModelTypeByTrimId(anyLong())).thenReturn(List.of());

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> modelOptionService.findModelTypeListByTrimId(anyLong());

            //then
            assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

}
