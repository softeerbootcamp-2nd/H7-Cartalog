package softeer.wantcar.cartalog.entity.model;

import softeer.wantcar.cartalog.entity.HMGData;
import softeer.wantcar.cartalog.entity.HashTag;

import java.util.List;

public class ModelOption {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String parentCategory;
    private String childCategory;
    private String imageUrl;
    private List<HashTag> hashTags;
    private List<HMGData> hmgData;
}
