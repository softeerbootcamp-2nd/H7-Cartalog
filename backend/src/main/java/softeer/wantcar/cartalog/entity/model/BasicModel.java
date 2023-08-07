package softeer.wantcar.cartalog.entity.model;

import softeer.wantcar.cartalog.entity.trim.Trim;

import java.util.List;

public class BasicModel {
    private Long id;
    private String name;
    private List<DetailModel> detailModels;
    private List<Trim> trims;
    private List<ModelExteriorColor> exteriorColors;
    private List<ModelInteriorColor> interiorColors;
    private List<ModelOption> options;
}
