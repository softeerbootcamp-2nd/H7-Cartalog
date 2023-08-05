package softeer.wantcar.cartalog.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import softeer.wantcar.cartalog.entity.Color;

@AllArgsConstructor
@Builder
public class ModelExteriorColor {
    private Long id;
    private Color color;
    private int price;
}
