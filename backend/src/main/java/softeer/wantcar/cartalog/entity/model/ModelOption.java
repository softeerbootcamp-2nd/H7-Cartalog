package softeer.wantcar.cartalog.entity.model;

import softeer.wantcar.cartalog.entity.OptionCategory;
import softeer.wantcar.cartalog.entity.hashtag.HashTag;

import java.util.List;

public class ModelOption {

    private Long id;
    private String name;
    private OptionCategory category;
    private ModelOptionType type;
    private String description;
    private int price;
    private String imageUrl;
    private float HMGData;
    private List<HashTag> hashTags;

}
