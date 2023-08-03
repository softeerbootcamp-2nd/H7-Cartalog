package softeer.wantcar.cartalog.entity.color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@AllArgsConstructor
public class TrimExteriorColor {

    @Getter
    private Long id;
    @Getter
    private int price;
    @Getter
    private String name;
    private List<TrimInteriorColor> interiorColors;
    @Getter
    private String code;
    @Getter
    private int chosen;

}
