package softeer.wantcar.cartalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class HMGData {
    private Long id;
    private String name;
    private String value;
    private String measure;
}
