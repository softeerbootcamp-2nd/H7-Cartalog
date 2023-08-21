package softeer.wantcar.cartalog.trim.repository.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TrimOptionChosenInfo {
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
    private int chosen;

    public static TrimOptionChosenInfo from(TrimOptionInfo trimOptionInfo, int chosen) {
        return TrimOptionChosenInfo.builder()
                .id(trimOptionInfo.getId())
                .name(trimOptionInfo.getName())
                .parentCategory(trimOptionInfo.getParentCategory())
                .childCategory(trimOptionInfo.getChildCategory())
                .price(trimOptionInfo.getPrice())
                .basic(trimOptionInfo.isBasic())
                .colorCondition(trimOptionInfo.isColorCondition())
                .trimInteriorColorIds(trimOptionInfo.getTrimInteriorColorIds())
                .hashTags(trimOptionInfo.getHashTags())
                .hasHMGData(trimOptionInfo.isHasHMGData())
                .chosen(chosen)
                .build();
    }
}
