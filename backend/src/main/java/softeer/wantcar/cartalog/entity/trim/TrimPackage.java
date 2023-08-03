package softeer.wantcar.cartalog.entity.trim;

import java.util.List;

public class TrimPackage implements TrimSelectable {

    private Long id;
    private String name;
    private String description;
    private TrimOptionType type;
    private int price;
    private String imageUrl;
    private List<TrimOption> options;

}
