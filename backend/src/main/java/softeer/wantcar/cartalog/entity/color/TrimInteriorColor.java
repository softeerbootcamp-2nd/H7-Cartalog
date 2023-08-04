package softeer.wantcar.cartalog.entity.color;

import lombok.Builder;
import lombok.Getter;

@Builder
public class TrimInteriorColor {

    @Getter
    private Long id;
    private InteriorColor color;
    @Getter
    private int price;
    @Getter
    private int chosen;

    public String getColorId() {
        return color.getId();
    }

    public String getName() {
        return color.getName();
    }

    public String getImageUrl() {
        return color.getImageUrl();
    }

}
