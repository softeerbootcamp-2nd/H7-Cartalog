package softeer.wantcar.cartalog.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import softeer.wantcar.cartalog.global.annotation.TestMethod;
import softeer.wantcar.cartalog.global.utils.CompareUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
public class ModelTypeListResponseDto {
    @Singular
    private List<ModelTypeDto> modelTypes;

    public int modelTypeSize() {
        return modelTypes.size();
    }

    @TestMethod
    public boolean equalAndAllContainTypes(List<String> type) {
        List<String> types = modelTypes.stream()
                .map(ModelTypeDto::getType).collect(Collectors.toUnmodifiableList());

        return CompareUtils.equalAndAllContain(type, types);
    }

    @TestMethod
    public boolean hasOptions(Map<String, List<String>> checkOptions) {
        return checkOptions.entrySet().stream()
                .allMatch((checkOptionMap) -> {
                    List<String> actualOptionNames = modelTypes.stream()
                            .filter(modelTypeDto -> modelTypeDto.getType().equals(checkOptionMap.getKey()))
                            .map(ModelTypeDto::getOptions)
                            .flatMap(Collection::stream)
                            .map(OptionDto::getName)
                            .collect(Collectors.toUnmodifiableList());
                    return CompareUtils.equalAndAllContain(checkOptionMap.getValue(), actualOptionNames);
                });
    }

    @TestMethod
    public boolean startWithUrl(String url) {
        return modelTypes.stream()
                .flatMap(modelTypeDto -> modelTypeDto.getOptions().stream())
                .allMatch(optionDto -> optionDto.startWithUrl(url));
    }
}
