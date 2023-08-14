package softeer.wantcar.cartalog.trim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.entity.HashTag;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;

import java.util.List;

@Getter
@Builder
public class TrimPackageDetailResponseDto extends OptionDetailResponseDto {
    private final boolean isPackage = true;
    private String name;
    private String imageUrl;
    private List<String> hashTags;
    private List<PackageOptionDetailDto> options;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PackageOptionDetailDto {
        private String name;
        private String description;
        private String imageUrl;
        private List<String> hashTags;
        private List<HMGDataDto> hmgData;
    }
}
