package softeer.wantcar.cartalog.estimate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class EstimateRequestDto {
    private Long detailTrimId;
    private String exteriorColorCode;
    private String interiorColorCode;
    private List<String> selectOptionOrPackageIds;

    public List<Long> getSelectOptionIds() {
        return getSelectIds('O');
    }

    public List<Long> getSelectPackageIds() {
        return getSelectIds('P');
    }

    private List<Long> getSelectIds(char prefix) {
        return getSelectOptionOrPackageIds().stream()
                .filter(id -> id.charAt(0) == prefix)
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}
