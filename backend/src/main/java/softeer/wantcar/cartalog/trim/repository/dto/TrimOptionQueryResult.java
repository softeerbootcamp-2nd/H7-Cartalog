package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TrimOptionQueryResult {
    private String id;
    private String name;
    private String parentCategory;
    private String childCategory;
    private String imageUrl;
    private int price;
    private boolean basic;
    private boolean colorCondition;
    private String trimInteriorColorCode;
    private String hashTag;
    private Long hmgModelOptionId;
}