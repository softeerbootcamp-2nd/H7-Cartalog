package softeer.wantcar.cartalog.entity.model;

import softeer.wantcar.cartalog.entity.color.InteriorColor;

import java.util.List;

public class Model {
    private Long id;
    private String name;
    private String type;
    private List<SubModel> subModels;
    private List<InteriorColor> interiorColors;
}
