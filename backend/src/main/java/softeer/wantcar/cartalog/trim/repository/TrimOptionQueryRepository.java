package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public interface TrimOptionQueryRepository {
    List<TrimOptionInfo> findPackagesByDetailTrimId(Long detailTrimId);

    @Getter
    @Builder
    @AllArgsConstructor
    class TrimOptionInfo {
        private String id;
        private String name;
        private String parentCategory;
        private String childCategory;
        private String imageUrl;
        private int price;
        private boolean basic;
        private boolean colorCondition;
        private List<Long> trimInteriorColorIds;
        private List<String> hashTags;
        private boolean hasHMGData;
    }

    List<String> findMultipleSelectCategories();
    List<TrimOptionInfo> findOptionsByDetailTrimId(Long detailTrimId);
}
