package softeer.wantcar.cartalog.trim.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;

import java.util.List;

public interface TrimOptionQueryRepository {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class TrimOptionInfo {
        private Long id;
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

    List<TrimOptionInfo> findOptionsByDetailTrimId(Long detailTrimId);
}
