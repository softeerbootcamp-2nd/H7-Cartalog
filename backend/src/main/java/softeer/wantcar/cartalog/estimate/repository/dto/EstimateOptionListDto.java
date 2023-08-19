package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class EstimateOptionListDto {
    private Long trimId;
    private List<Long> optionIds;
    private List<Long> packageIds;

    public List<String> getAllOptionIds() {
        List<String> totalOptionIds = new ArrayList<>();
        totalOptionIds.addAll(getFormattedOptionId(optionIds, true));
        totalOptionIds.addAll(getFormattedOptionId(packageIds, false));
        return totalOptionIds;
    }

    private List<String> getFormattedOptionId(List<Long> optionIds, boolean isOption) {
        String optionPrefix = isOption ? "O" : "P";
        return optionIds.stream()
                .map(id -> optionPrefix + id)
                .collect(Collectors.toList());
    }
}
