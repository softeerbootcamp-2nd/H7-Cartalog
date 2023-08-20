package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionPackageInfoDto {
    private Long id;
    private String name;
    private String childCategory;
    private int price;
    private String imageUrl;
}
