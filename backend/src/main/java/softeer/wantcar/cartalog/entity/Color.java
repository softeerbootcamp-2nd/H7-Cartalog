package softeer.wantcar.cartalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Color {
    private String id;
    private String name;
    private String code;
}
