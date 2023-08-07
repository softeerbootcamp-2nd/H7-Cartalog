package softeer.wantcar.cartalog.trim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class TrimPackageDetailResponseDto extends OptionDetailResponseDto {
    private boolean isPackage;
    private List<PackageOptionDetailDto> options;

    @Getter
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class PackageOptionDetailDto {
        private String name;
        private String description;
        private List<String> hashTags;
        private List<HMGDataDto> hmgData;
    }
}
