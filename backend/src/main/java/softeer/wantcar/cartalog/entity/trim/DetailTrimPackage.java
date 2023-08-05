package softeer.wantcar.cartalog.entity.trim;

import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;

import java.util.List;

public class DetailTrimPackage {
    private Long id;
    private int price;
    private String description;
    private String type;
    private boolean colorCondition;
    private String imageUrl;
    private List<DetailTrimOption> options;
    private List<ModelInteriorColor> possibleColors;

}
