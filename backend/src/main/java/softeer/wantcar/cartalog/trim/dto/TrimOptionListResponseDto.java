package softeer.wantcar.cartalog.trim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;

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

        public TrimOptionDto(TrimOptionQueryRepository.TrimOptionInfo trimOptionInfo, int chosen) {
            this.id = trimOptionInfo.getId();
            this.name = trimOptionInfo.getName();
            this.parentCategory = trimOptionInfo.getParentCategory();
            this.childCategory = trimOptionInfo.getChildCategory();
            this.imageUrl = trimOptionInfo.getImageUrl();
            this.price = trimOptionInfo.getPrice();
            this.chosen = chosen;
            this.hashTags = trimOptionInfo.getHashTags();
            this.hasHMGData = trimOptionInfo.isHasHMGData();
        }
    }
}
