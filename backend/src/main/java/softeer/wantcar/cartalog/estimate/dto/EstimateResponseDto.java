package softeer.wantcar.cartalog.estimate.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EstimateResponseDto {
    private Long trimId;
    private Long detailTrimId;
    private String exteriorColorCode;
    private String interiorColorCode;
    private List<String> modelOptions;
    private List<String> selectOptionOrPackages;

    public static class EstimateResponseDtoBuilder {
        @Setter(AccessLevel.PRIVATE)
        private Long trimId;
        @Setter(AccessLevel.PRIVATE)
        private Long detailTrimId;
        @Setter(AccessLevel.PRIVATE)
        private String exteriorColorCode;
        @Setter(AccessLevel.PRIVATE)
        private String interiorColorCode;
        private final List<String> modelOptions = new ArrayList<>();
        private final List<String> selectOptionOrPackages = new ArrayList<>();

        public static EstimateResponseDtoBuilder builder() {
            return new EstimateResponseDtoBuilder();
        }

        public EstimateResponseDtoBuilder trimId(Long trimId) {
            setTrimId(trimId);
            return this;
        }

        public EstimateResponseDtoBuilder detailTrimId(Long detailTrimId) {
            setDetailTrimId(detailTrimId);
            return this;
        }

        public EstimateResponseDtoBuilder exteriorColorCode(String exteriorColorCode) {
            setExteriorColorCode(exteriorColorCode);
            return this;
        }

        public EstimateResponseDtoBuilder interiorColorCode(String interiorColorCode) {
            setInteriorColorCode(interiorColorCode);
            return this;
        }

        public EstimateResponseDtoBuilder modelOptionId(Long modelOptionId) {
            modelOptions.add("O" + modelOptionId);
            return this;
        }

        public EstimateResponseDtoBuilder selectOptionId(Long selectOptionId) {
            selectOptionOrPackages.add("O" + selectOptionId);
            return this;
        }

        public EstimateResponseDtoBuilder selectPackageId(Long selectPackageId) {
            selectOptionOrPackages.add("P" + selectPackageId);
            return this;
        }

        public EstimateResponseDto build() {
            return new EstimateResponseDto(trimId, detailTrimId, exteriorColorCode, interiorColorCode, modelOptions, selectOptionOrPackages);
        }
    }
}
