package softeer.wantcar.cartalog.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.entity.Color;

@AllArgsConstructor
@Builder
@Getter
public class ModelExteriorColor {
    private Long id;
    private Color color;
    private int price;
}
