package softeer.wantcar.cartalog.entity.trim;

import java.util.List;

public class DetailTrim {
    private Long id;
    private Trim trim;
    private int minPrice;
    private int maxPrice;
    private List<DetailTrimOption> options;
    private List<DetailTrimPackage> packages;
}
