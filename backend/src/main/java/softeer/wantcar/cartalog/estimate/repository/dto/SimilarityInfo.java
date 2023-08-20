package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class SimilarityInfo {
    private long idx;
    private Double similarity;
}
