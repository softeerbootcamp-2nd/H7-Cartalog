package softeer.wantcar.cartalog.estimate.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PendingHashTagSimilaritySaveDto {
    private String hashTagKey;
    private String pendingHashTagLeftKey;
    private Long trimId;
}
