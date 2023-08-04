package softeer.wantcar.cartalog.entity.trim;

import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;
import softeer.wantcar.cartalog.entity.color.TrimInteriorColor;

import java.util.List;

public class Trim {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private List<SubModelTrim> subModelTrims;
    private List<TrimExteriorColor> exteriorColors;
    private List<TrimInteriorColor> interiorColors;
}
