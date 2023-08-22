package softeer.wantcar.cartalog.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.dto.TrimExteriorColorQueryResult;
import softeer.wantcar.cartalog.trim.repository.dto.TrimInteriorColorQueryResult;

import java.util.List;
import java.util.stream.Collectors;

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

        List<ChosenDto> chosenDtoList = chosenRepository.findExteriorColorChosenByExteriorColorCode(exteriorColorCodes);

        TrimExteriorColorListResponseDto.TrimExteriorColorListResponseDtoBuilder builder = TrimExteriorColorListResponseDto.builder();
        trimExteriorColors.stream()
                .map(exteriorColor -> exteriorColor.toTrimExteriorColorDto(
                        ChosenDto.findChosenDtoById(chosenDtoList, exteriorColor.getCode()).getChosen()))
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

        List<ChosenDto> chosenDtoList = chosenRepository.findInteriorColorChosenByInteriorColorCode(interiorColorCodes);
        TrimInteriorColorListResponseDto.TrimInteriorColorListResponseDtoBuilder builder = TrimInteriorColorListResponseDto.builder();
        trimInteriorColors.stream()
                .map(interiorColor -> interiorColor.toTrimInteriorColorDto(
                        ChosenDto.findChosenDtoById(chosenDtoList, interiorColor.getCode()).getChosen()))
                .forEach(builder::trimInteriorColorDto);

        return builder.build();
    }
}
