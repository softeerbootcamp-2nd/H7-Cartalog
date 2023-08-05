package softeer.wantcar.cartalog.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ModelInteriorColor {
    private String id;
    private String name;
    private int price;
    private String imageUrl;
}
