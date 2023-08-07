package softeer.wantcar.cartalog.entity.trim;

import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;

import java.util.List;

public class Trim {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private int minPrice;
    private int maxPrice;
    private List<ModelExteriorColor> exteriorColors;
    private List<ModelInteriorColor> interiorColors;
}
