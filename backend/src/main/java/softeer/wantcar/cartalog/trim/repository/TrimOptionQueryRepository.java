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
        private List<String> trimInteriorColorIds;
        private List<String> hashTags;
        private boolean hasHMGData;
    }

    List<String> findMultipleSelectableCategories();

    List<TrimOptionInfo> findOptionsByDetailTrimId(Long detailTrimId);

    @Getter
    @AllArgsConstructor
    class ModelOptionInfo {
        private String name;
        private String description;
        private String imageUrl;
    }

    ModelOptionInfo findModelOptionInfoByOptionId(Long optionId);

    List<String> findHashTagsByOptionId(Long optionId);

    @Getter
    @AllArgsConstructor
    class HMGDataInfo {
        private String name;
        private String val;
        private String measure;
        private String unit;
    }

    List<HMGDataInfo> findHMGDataInfoListByOptionId(Long optionId);

    @Getter
    @AllArgsConstructor
    class DetailTrimPackageInfo {
        private String name;
        private String imageUrl;
    }

    DetailTrimPackageInfo findDetailTrimPackageInfoByPackageId(Long packageId);

    List<String> findPackageHashTagByPackageId(Long packageId);

    List<Long> findModelOptionIdsByPackageId(Long packageId);
}
