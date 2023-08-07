package softeer.wantcar.cartalog.trim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
        private Long id;
        private String name;
        private String parentCategory;
        private String childCategory;
        private String imageUrl;
        private int price;
        private int chosen;
        private List<String> hashTags;
    }
}
