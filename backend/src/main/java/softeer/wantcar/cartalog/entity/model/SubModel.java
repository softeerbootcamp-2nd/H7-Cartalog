package softeer.wantcar.cartalog.entity.model;

import softeer.wantcar.cartalog.entity.option.SubModelOption;
import softeer.wantcar.cartalog.entity.option.TrimPackage;
import softeer.wantcar.cartalog.entity.trim.SubModelTrim;

import java.util.List;

public class SubModel {
    private Long id;
    private float displacement;
    private float fullEfficiency;
    private List<SubModelTrim> trims;
    private List<SubModelOption> options;
    private List<TrimPackage> packages;
}
