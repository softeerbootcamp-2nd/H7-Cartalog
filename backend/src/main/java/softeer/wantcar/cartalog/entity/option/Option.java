package softeer.wantcar.cartalog.entity.option;

import softeer.wantcar.cartalog.entity.HashTag;

import java.util.List;

public class Option {
    private Long id;
    private String name;
    private String type;
    private String category;
    private String description;
    private String imageUrl;
    private List<SubModelOption> subModelOptions;
    private List<TrimOption> trimOptions;
    private List<HashTag> hashTags;
}
