package softeer.wantcar.cartalog.entity.trim;

import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelOption;

import java.util.List;

public class DetailTrimOption {
    private Long id;
    private ModelOption modelOption;
    private int price;
    private boolean colorCondition;
    private boolean visibility;
    private boolean basic;
    private List<ModelInteriorColor> possibleColors;
}
