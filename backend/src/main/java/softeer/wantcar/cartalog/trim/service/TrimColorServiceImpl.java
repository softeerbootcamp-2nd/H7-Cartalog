package softeer.wantcar.cartalog.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import softeer.wantcar.cartalog.chosen.ChosenConfig;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.dto.TrimExteriorColorQueryResult;
import softeer.wantcar.cartalog.trim.repository.dto.TrimInteriorColorQueryResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TrimColorServiceImpl implements TrimColorService {
    private final TrimColorQueryRepository trimColorQueryRepository;
    private final ChosenRepository chosenRepository;

    @Override
    public TrimExteriorColorListResponseDto findTrimExteriorColor(Long trimId) {
        List<TrimExteriorColorQueryResult> trimExteriorColors = trimColorQueryRepository.findTrimExteriorColorsByTrimId(trimId);

        if (trimExteriorColors.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<String> exteriorColorCodes = trimExteriorColors.stream()
                .map(TrimExteriorColorQueryResult::getCode)
                .collect(Collectors.toUnmodifiableList());

        List<Integer> exteriorColorChosen = chosenRepository.findExteriorColorChosenByExteriorColorCode(exteriorColorCodes, ChosenConfig.CHOSEN_DAYS);

        TrimExteriorColorListResponseDto.TrimExteriorColorListResponseDtoBuilder builder = TrimExteriorColorListResponseDto.builder();

        IntStream.range(0, trimExteriorColors.size())
                .mapToObj(index -> trimExteriorColors.get(index).toTrimExteriorColorDto(exteriorColorChosen.get(index)))
                .forEach(builder::trimExteriorColorDto);

        return builder.build();
    }

    @Override
    public TrimInteriorColorListResponseDto findTrimInteriorColor(Long trimId, String exteriorColorCode) {
        List<TrimInteriorColorQueryResult> trimInteriorColors = trimColorQueryRepository.findTrimInteriorColorsByTrimIdAndExteriorColorCode(trimId, exteriorColorCode);

        if (trimInteriorColors.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<String> interiorColorCodes = trimInteriorColors.stream()
                .map(TrimInteriorColorQueryResult::getCode)
                .collect(Collectors.toUnmodifiableList());

        List<Integer> interiorColorCods = chosenRepository.findInteriorColorChosenByInteriorColorCode(exteriorColorCode, interiorColorCodes, ChosenConfig.CHOSEN_DAYS);

        TrimInteriorColorListResponseDto.TrimInteriorColorListResponseDtoBuilder builder = TrimInteriorColorListResponseDto.builder();

        IntStream.range(0, trimInteriorColors.size())
                .mapToObj(index -> trimInteriorColors.get(index).toTrimInteriorColorDto(interiorColorCods.get(index)))
                .forEach(builder::trimInteriorColorDto);

        return builder.build();
    }
}
