package softeer.wantcar.cartalog.estimate.dao;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstimateDao {
    private Long detailTrimId;
    private Long trimExteriorColorId;
    private Long trimInteriorColorId;
    private List<Long> modelOptionIds;
    private List<Long> modelPackageIds;
}
