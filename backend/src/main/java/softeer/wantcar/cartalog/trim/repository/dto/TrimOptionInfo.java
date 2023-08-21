package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TrimOptionInfo {
    private String id;
    private String name;
    private String parentCategory;
    private String childCategory;
    private String imageUrl;
    private int price;
    private boolean basic;
    private boolean colorCondition;
    private List<String> trimInteriorColorIds;
    private List<String> hashTags;
    private boolean hasHMGData;
}
