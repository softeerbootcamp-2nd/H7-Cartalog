package softeer.wantcar.cartalog.estimate.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PendingHashTagSimilaritySaveDto {
    private String hashTagKey;
    private List<String> pendingHashTagLeftKeys;
    private Long trimId;
}
