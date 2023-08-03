package softeer.wantcar.cartalog.entity.trim;

import softeer.wantcar.cartalog.entity.OptionCategory;
import softeer.wantcar.cartalog.entity.hashtag.HashTag;

import java.util.List;

public class TrimOption implements TrimSelectable {

    private Long id;
    private String name;
    private TrimOptionType type;
    private OptionCategory category;
    private String description;
    private int price;
    private String imageUrl;
    private boolean basic;
    private float HMGData;
    private boolean visibility;
    private List<HashTag> hashTags;

}
