package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TrimPackageDetailResponseDto extends OptionDetailResponseDto {
    private final boolean isPackage = true;
    private String name;
    private String imageUrl;
    private List<String> hashTags;
    private List<TrimOptionDetailResponseDto> options;
}
