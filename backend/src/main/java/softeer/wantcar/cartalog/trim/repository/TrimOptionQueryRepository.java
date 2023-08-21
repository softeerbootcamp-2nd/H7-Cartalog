package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.repository.dto.OptionPackageInfoDto;
import softeer.wantcar.cartalog.trim.repository.dto.TrimOptionInfo;

import java.util.List;

public interface TrimOptionQueryRepository {
    List<TrimOptionInfo> findPackagesByDetailTrimId(Long detailTrimId);

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

    List<OptionPackageInfoDto> findOptionPackageInfoByOptionPackageIds(List<Long> optionIds, List<Long> packageIds);
}
