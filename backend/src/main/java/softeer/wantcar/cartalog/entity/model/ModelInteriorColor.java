package softeer.wantcar.cartalog.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ModelInteriorColor {
    private String id;
    private String name;
    private int price;
    private String imageUrl;
}
