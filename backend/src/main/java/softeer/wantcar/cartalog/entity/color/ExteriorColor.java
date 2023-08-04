package softeer.wantcar.cartalog.entity.color;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class ExteriorColor {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String code;
    private List<TrimExteriorColor> trimExteriorColors;
}
