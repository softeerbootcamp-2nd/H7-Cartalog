package softeer.wantcar.cartalog.entity.trim;

import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;
import softeer.wantcar.cartalog.entity.model.Model;

import java.util.List;

public class Trim {

    private Long id;
    private Model model;
    private String description;
    private int minPrice;
    private int maxPrice;
    private List<TrimSelectable> trimSelectables;
    private List<TrimExteriorColor> exteriorColors;

}
