package softeer.wantcar.cartalog.entity.color;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class TrimExteriorColor {

    @Getter
    private Long id;
    private ExteriorColor color;
    @Getter
    private int price;
    @Getter
    private int chosen;
    private List<TrimInteriorColor> trimInteriorColors;

    public String getColorId() {
        return color.getId();
    }

    public String getName() {
        return color.getName();
    }

    public String getCode() {
        return color.getCode();
    }

}
