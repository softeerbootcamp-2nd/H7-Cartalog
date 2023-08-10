package softeer.wantcar.cartalog.model.dto;

import lombok.*;
import softeer.wantcar.cartalog.global.annotation.TestMethod;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ModelTypeListResponseDto {
    @Singular
    private List<ModelTypeDto> modelTypes;

    public int modelTypeSize() {
        return modelTypes.size();
    }

    public boolean equalAndAllContain(List<String> expected, List<String> actual) {
        return expected.size() == actual.size() && new HashSet<>(expected).equals(new HashSet<>(actual));
    }

    @TestMethod
    public boolean equalAndAllContainTypes(List<String> type) {
        List<String> types = modelTypes.stream()
                .map(ModelTypeDto::getType).collect(Collectors.toUnmodifiableList());

        return equalAndAllContain(type, types);
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
                    return equalAndAllContain(checkOptionMap.getValue(), actualOptionNames);
                });
    }
}
