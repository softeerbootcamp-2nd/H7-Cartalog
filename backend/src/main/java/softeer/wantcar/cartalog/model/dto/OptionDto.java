package softeer.wantcar.cartalog.model.dto;

import lombok.*;
import softeer.wantcar.cartalog.global.annotation.TestMethod;
import softeer.wantcar.cartalog.global.dto.HMGDataDtoInterface;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OptionDto {
    private Long id;
    private String name;
    private int price;
    private int chosen;
    private String imageUrl;
    private String description;
    @Singular("hmgDatum")
    private List<HMGDataDtoInterface> hmgData;

    @TestMethod
    public boolean startWithUrl(String url) {
        return imageUrl.startsWith(url);
    }
}
