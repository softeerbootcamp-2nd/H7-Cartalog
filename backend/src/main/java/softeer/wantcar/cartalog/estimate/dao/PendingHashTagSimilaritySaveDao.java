package softeer.wantcar.cartalog.estimate.dao;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PendingHashTagSimilaritySaveDao {
    private String hashTagKey;
    private String pendingHashTagLeftKey;
    private Long trimId;
}
