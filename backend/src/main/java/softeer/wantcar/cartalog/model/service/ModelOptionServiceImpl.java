package softeer.wantcar.cartalog.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import softeer.wantcar.cartalog.chosen.ChosenConfig;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
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
import java.util.stream.IntStream;

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
        List<Long> modelTypeIds = modelTypeDtoList.stream()
                .map(ModelTypeDto::getModelOptionId)
                .distinct()
                .collect(Collectors.toUnmodifiableList());

        List<Integer> modelTypeChosen = chosenRepository.findModelTypeChosenByOptionId(modelTypeIds, ChosenConfig.CHOSEN_DAYS);

        Map<Long, Integer> chosenMap = new HashMap<>();
        IntStream.range(0, modelTypeIds.size())
                .forEach(index -> chosenMap.put(modelTypeIds.get(index), modelTypeChosen.get(index)));
        ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder builder = buildModelTypeListResponseDto(modelTypeDtoList, chosenMap);
        return builder.build();
    }

    private static ModelTypeListResponseDto.ModelTypeListResponseDtoBuilder buildModelTypeListResponseDto(List<ModelTypeDto> modelTypeDtoList, Map<Long, Integer> chosenMap) {
        Map<String, Map<Long, OptionDto.OptionDtoBuilder>> dtoBuilderMap = new HashMap<>();

        modelTypeDtoList.forEach(mapper -> {
            Map<Long, OptionDto.OptionDtoBuilder> optionDtoBuilderMap = dtoBuilderMap.getOrDefault(mapper.getChildCategory(), new HashMap<>());
            OptionDto.OptionDtoBuilder optionDtoBuilder = optionDtoBuilderMap.getOrDefault(mapper.getModelOptionId(),
                    OptionDto.builder()
                            .id(mapper.getModelOptionId())
                            .name(mapper.getName())
                            .price(mapper.getPrice())
                            .imageUrl(mapper.getImageUrl())
                            .chosen(chosenMap.get(mapper.getModelOptionId()))
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
