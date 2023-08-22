package softeer.wantcar.cartalog.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;
import softeer.wantcar.cartalog.global.dto.HMGDataDtoInterface;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeOptionDto;
import softeer.wantcar.cartalog.model.dto.OptionDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.model.repository.dto.ModelTypeDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelOptionServiceImpl implements ModelOptionService {
    private final ModelOptionQueryRepository modelOptionQueryRepository;
    private final ChosenRepository chosenRepository;

    @Override
    public ModelTypeListResponseDto findModelTypeListByTrimId(Long trimId) {
        List<ModelTypeDto> modelTypeDtoList = modelOptionQueryRepository.findModelTypeByTrimId(trimId);
        if (modelTypeDtoList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<String> modelTypeIds = getModelTypeIds(modelTypeDtoList);
        List<ChosenDto> modelTypeChosen = chosenRepository.findModelTypeChosenByOptionId(modelTypeIds);

        Map<String, Integer> chosenMap = new HashMap<>();
        modelTypeChosen.forEach(chosen -> chosenMap.put(chosen.getIdCode(), chosen.getChosen()));
        ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder builder = buildModelTypeListResponseDto(modelTypeDtoList, chosenMap);
        return builder.build();
    }

    private static List<String> getModelTypeIds(List<ModelTypeDto> modelTypeDtoList) {
        return modelTypeDtoList.stream()
                .map(modelType -> "O" + modelType.getModelOptionId())
                .distinct()
                .sorted()
                .collect(Collectors.toUnmodifiableList());
    }

    private static ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder buildModelTypeListResponseDto(List<ModelTypeDto> modelTypeDtoList,
                                                                                                          Map<String, Integer> chosenMap) {
        Map<String, Map<Long, OptionDto.OptionDtoBuilder>> dtoBuilderMap = new HashMap<>();

        modelTypeDtoList.forEach(mapper -> {
            Map<Long, OptionDto.OptionDtoBuilder> optionDtoBuilderMap = dtoBuilderMap.getOrDefault(mapper.getChildCategory(), new HashMap<>());
            OptionDto.OptionDtoBuilder optionDtoBuilder = optionDtoBuilderMap.getOrDefault(mapper.getModelOptionId(),
                    OptionDto.builder()
                            .id(mapper.getModelOptionId())
                            .name(mapper.getName())
                            .price(mapper.getPrice())
                            .imageUrl(mapper.getImageUrl())
                            .chosen(chosenMap.get("O" + mapper.getModelOptionId()))
                            .description(mapper.getDescription()));
            HMGDataDtoInterface hmgDataDto = mapper.toHMGDataDto();
            if (hmgDataDto != null) {
                optionDtoBuilder.hmgDatum(mapper.toHMGDataDto());
            }
            optionDtoBuilderMap.put(mapper.getModelOptionId(), optionDtoBuilder);
            dtoBuilderMap.put(mapper.getChildCategory(), optionDtoBuilderMap);
        });

        ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder builder = ModelTypeListResponseDto.builder();
        dtoBuilderMap.forEach((type, optionDtoBuilderMap) -> {
            ModelTypeOptionDto.ModelTypeOptionDtoBuilder modelTypeDtoBuilder = ModelTypeOptionDto.builder().type(type);
            optionDtoBuilderMap.forEach((modelOptionId, optionDtoBuilder) -> modelTypeDtoBuilder.option(optionDtoBuilder.build()));
            builder.modelType(modelTypeDtoBuilder.build());
        });
        return builder;
    }
}
