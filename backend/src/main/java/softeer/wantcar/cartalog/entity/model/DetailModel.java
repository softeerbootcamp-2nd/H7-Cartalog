package softeer.wantcar.cartalog.entity.model;

import softeer.wantcar.cartalog.entity.trim.DetailTrim;

import java.util.List;

public class DetailModel {
    private Long id;
    private float displacement;
    private float fuelEfficiency;
    private List<DetailModelDecisionOption> decisionOptions;
    private List<DetailTrim> detailTrims;
}
