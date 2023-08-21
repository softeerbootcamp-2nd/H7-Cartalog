package softeer.wantcar.cartalog.trim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.repository.dto.TrimOptionChosenInfo;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class TrimOptionListResponseDto {
    private List<String> multipleSelectParentCategory;
    private List<TrimOptionDto> selectOptions;
    private List<TrimOptionDto> defaultOptions;

    @Getter
    @Builder
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class TrimOptionDto {
        private String id;
        private String name;
        private String parentCategory;
        private String childCategory;
        private String imageUrl;
        private int price;
        private int chosen;
        private List<String> hashTags;
        private boolean hasHMGData;

        public TrimOptionDto(TrimOptionChosenInfo trimOptionChosenInfo) {
            this.id = trimOptionChosenInfo.getId();
            this.name = trimOptionChosenInfo.getName();
            this.parentCategory = trimOptionChosenInfo.getParentCategory();
            this.childCategory = trimOptionChosenInfo.getChildCategory();
            this.imageUrl = trimOptionChosenInfo.getImageUrl();
            this.price = trimOptionChosenInfo.getPrice();
            this.chosen = trimOptionChosenInfo.getChosen();
            this.hashTags = trimOptionChosenInfo.getHashTags();
            this.hasHMGData = trimOptionChosenInfo.isHasHMGData();
        }
    }
}
