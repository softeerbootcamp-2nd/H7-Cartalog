package softeer.wantcar.cartalog.trim.dto;

import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;

import java.util.List;

@Getter
@Builder
public class TrimOptionDetailResponseDto extends OptionDetailResponseDto {
    private final boolean isPackage = false;
    private String name;
    private String description;
    private String imageUrl;
    private List<String> hashTags;
    private List<HMGDataDto> hmgData;
}
