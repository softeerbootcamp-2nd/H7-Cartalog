package softeer.wantcar.cartalog.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.entity.trim.Trim;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class BasicModel {
    private Long id;
    private String name;
    private String category;
    private List<DetailModel> detailModels;
    private List<Trim> trims;
    private List<ModelExteriorColor> exteriorColors;
    private List<ModelInteriorColor> interiorColors;
    private List<ModelOption> options;
}
