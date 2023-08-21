package softeer.wantcar.cartalog.trim.service;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import softeer.wantcar.cartalog.chosen.ChosenConfig;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.dto.TrimExteriorColorQueryResult;
import softeer.wantcar.cartalog.trim.repository.dto.TrimInteriorColorQueryResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("트림 색상 서비스 테스트")
@ExtendWith(SoftAssertionsExtension.class)
class TrimColorServiceTest {
    @InjectSoftAssertions
    SoftAssertions softAssertions;
    TrimColorService trimColorService;
    TrimColorQueryRepository trimColorQueryRepository;
    ChosenRepository chosenRepository;

    @BeforeEach
    void setUp() {
        trimColorQueryRepository = mock(TrimColorQueryRepository.class);
        chosenRepository = mock(ChosenRepository.class);
        trimColorService = new TrimColorServiceImpl(trimColorQueryRepository, chosenRepository);
    }

    @Nested
    @DisplayName("외장 색상 테스트")
    class findTrimExteriorColorTest {
        @Test
        @DisplayName("적절한 트림 식별자를 전달했을 때 외장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            TrimExteriorColorQueryResult exteriorColor1 = TrimExteriorColorQueryResult.builder().code("A1").build();
            TrimExteriorColorQueryResult exteriorColor2 = TrimExteriorColorQueryResult.builder().code("B2").build();
            List<TrimExteriorColorQueryResult> trimExteriorColors = List.of(exteriorColor1, exteriorColor2);
            when(trimColorQueryRepository.findTrimExteriorColorsByTrimId(anyLong())).thenReturn(trimExteriorColors);

            List<String> exteriorColorCodes = List.of("A1", "B2");
            List<Integer> exteriorColorChosen = List.of(10, 20);
            when(chosenRepository.findExteriorColorChosenByExteriorColorCode(exteriorColorCodes, ChosenConfig.CHOSEN_DAYS))
                    .thenReturn(exteriorColorChosen);

            //when
            TrimExteriorColorListResponseDto trimExteriorColor = trimColorService.findTrimExteriorColor(anyLong());

            //then
            List<String> actualColorCodes = trimExteriorColor.getExteriorColors().stream()
                    .map(TrimExteriorColorListResponseDto.TrimExteriorColorDto::getCode)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(actualColorCodes.containsAll(exteriorColorCodes)).isTrue();
            List<Integer> actualColorChosen = trimExteriorColor.getExteriorColors().stream()
                    .map(TrimExteriorColorListResponseDto.TrimExteriorColorDto::getChosen)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(actualColorChosen.containsAll(exteriorColorChosen)).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자를 전달했을 경우 에러를 던져야 한다.")
        void failure() {
            //given
            when(trimColorQueryRepository.findTrimExteriorColorsByTrimId(anyLong())).thenReturn(List.of());

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> trimColorService.findTrimExteriorColor(anyLong());

            //then
            softAssertions.assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("내장 색상 테스트")
    class findTrimInteriorColorTest {
        @Test
        @DisplayName("적절한 트림 식별자와 외장 색상 코드를 전달했을 때 내장 색상 리스트를 반환해야 한다.")
        void success() {
            //given
            TrimInteriorColorQueryResult interiorColor1 = TrimInteriorColorQueryResult.builder().code("A1").build();
            TrimInteriorColorQueryResult interiorColor2 = TrimInteriorColorQueryResult.builder().code("B2").build();
            List<TrimInteriorColorQueryResult> trimInteriorColors = List.of(interiorColor1, interiorColor2);
            String exteriorColorCode = "HelloWorld";
            when(trimColorQueryRepository.findTrimInteriorColorsByTrimIdAndExteriorColorCode(1L, exteriorColorCode)).thenReturn(trimInteriorColors);

            List<String> interiorColorCodes = List.of("A1", "B2");
            List<Integer> interiorColorChosen = List.of(10, 20);
            when(chosenRepository.findInteriorColorChosenByInteriorColorCode(exteriorColorCode, interiorColorCodes, ChosenConfig.CHOSEN_DAYS))
                    .thenReturn(interiorColorChosen);

            //when
            TrimInteriorColorListResponseDto trimInteriorColor = trimColorService.findTrimInteriorColor(1L, exteriorColorCode);

            //then
            List<String> actualColorCodes = trimInteriorColor.getInteriorColors().stream()
                    .map(TrimInteriorColorListResponseDto.TrimInteriorColorDto::getCode)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(actualColorCodes.containsAll(interiorColorCodes)).isTrue();
            List<Integer> actualColorChosen = trimInteriorColor.getInteriorColors().stream()
                    .map(TrimInteriorColorListResponseDto.TrimInteriorColorDto::getChosen)
                    .collect(Collectors.toUnmodifiableList());
            softAssertions.assertThat(actualColorChosen.containsAll(interiorColorChosen)).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 트림 식별자 혹은 외장 색상 코드를 전달했을 경우 에러를 던져야 한다.")
        void failure() {
            //given
            when(trimColorQueryRepository.findTrimInteriorColorsByTrimIdAndExteriorColorCode(anyLong(), anyString())).thenReturn(List.of());

            //when
            ThrowableAssert.ThrowingCallable runnable = () -> trimColorService.findTrimInteriorColor(anyLong(), anyString());

            //then
            softAssertions.assertThatThrownBy(runnable).isInstanceOf(IllegalArgumentException.class);
        }
    }

}