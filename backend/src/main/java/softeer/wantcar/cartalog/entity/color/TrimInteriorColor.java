package softeer.wantcar.cartalog.entity.color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrimInteriorColor {

    private Long id;
    private int price;
    private String name;
    private String imageUrl;
    private int chosen;

}
