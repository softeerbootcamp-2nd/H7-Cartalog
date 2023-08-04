package softeer.wantcar.cartalog.entity.color;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class InteriorColor {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String imageUrl;
    private List<TrimInteriorColor> trimInteriorColors;
}
