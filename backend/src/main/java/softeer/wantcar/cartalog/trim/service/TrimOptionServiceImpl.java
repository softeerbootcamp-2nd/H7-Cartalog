package softeer.wantcar.cartalog.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrimOptionServiceImpl implements TrimOptionService {
    private final TrimOptionQueryRepository trimOptionQueryRepository;

    @Override
    public TrimOptionListResponseDto getTrimOptionList(Long detailTrimId, String interiorColorCode) {
        List<String> multipleSelectableCategories = trimOptionQueryRepository.findMultipleSelectableCategories();
        List<TrimOptionQueryRepository.TrimOptionInfo> options = trimOptionQueryRepository.findOptionsByDetailTrimId(detailTrimId);
        List<TrimOptionQueryRepository.TrimOptionInfo> packages = trimOptionQueryRepository.findPackagesByDetailTrimId(detailTrimId);
        if (Objects.isNull(options) || Objects.isNull(packages)) {
            return null;
        }

        Map<Boolean, List<TrimOptionQueryRepository.TrimOptionInfo>> collectedOptions = options.stream()
                .filter(option -> filterColorCondition(interiorColorCode, option))
                .collect(Collectors.groupingBy(TrimOptionQueryRepository.TrimOptionInfo::isBasic));

        return TrimOptionListResponseDto.builder()
                .multipleSelectParentCategory(multipleSelectableCategories)
                .defaultOptions(getDefaultOptions(collectedOptions))
                .selectOptions(getSelectableOptions(packages, collectedOptions))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TrimOptionDetailResponseDto getTrimOptionDetail(Long detailTrimOptionId) {
        Long optionId = trimOptionQueryRepository.findModelOptionIdByDetailTrimOptionId(detailTrimOptionId);
        return optionId != null ? getTrimOptionDetailByOptionId(optionId) : null;
    }

    private TrimOptionDetailResponseDto getTrimOptionDetailByOptionId(Long optionId) {
        TrimOptionQueryRepository.ModelOptionInfo modelOption = trimOptionQueryRepository.findModelOptionInfoByOptionId(optionId);
        List<String> hashTags = trimOptionQueryRepository.findHashTagsByOptionId(optionId);
        List<TrimOptionQueryRepository.HMGDataInfo> hmgDataList = trimOptionQueryRepository.findHMGDataInfoListByOptionId(optionId);
        List<HMGDataDto> hmgDataDtoList = buildHmgDataDtoList(hmgDataList);

        return TrimOptionDetailResponseDto.builder()
                .name(modelOption.getName())
                .description(modelOption.getDescription())
                .imageUrl(modelOption.getImageUrl())
                .hashTags(hashTags)
                .hmgData(hmgDataDtoList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TrimPackageDetailResponseDto getTrimPackageDetail(Long packageId) {
        TrimOptionQueryRepository.DetailTrimPackageInfo detailTrimPackageInfos = trimOptionQueryRepository.findDetailTrimPackageInfoByPackageId(packageId);
        if (Objects.isNull(detailTrimPackageInfos)) {
            return null;
        }

        List<String> hashTags = trimOptionQueryRepository.findPackageHashTagByPackageId(packageId);

        List<Long> modelOptionIds = trimOptionQueryRepository.findModelOptionIdsByPackageId(packageId);
        List<TrimOptionDetailResponseDto> trimOptionDetailResponseDtoList = modelOptionIds.stream()
                .map(this::getTrimOptionDetailByOptionId)
                .collect(Collectors.toUnmodifiableList());

        return TrimPackageDetailResponseDto.builder()
                .name(detailTrimPackageInfos.getName())
                .imageUrl(detailTrimPackageInfos.getImageUrl())
                .hashTags(hashTags)
                .options(trimOptionDetailResponseDtoList)
                .build();
    }

    private boolean filterColorCondition(String interiorColorCode, TrimOptionQueryRepository.TrimOptionInfo option) {
        return !option.isColorCondition() || option.getTrimInteriorColorIds().contains(interiorColorCode);
    }

    private List<TrimOptionListResponseDto.TrimOptionDto> getDefaultOptions(Map<Boolean, List<TrimOptionQueryRepository.TrimOptionInfo>> collectedOptions) {
        return getOptions(collectedOptions.getOrDefault(true, new ArrayList<>()));
    }

    private List<TrimOptionListResponseDto.TrimOptionDto> getSelectableOptions(List<TrimOptionQueryRepository.TrimOptionInfo> packages,
                                                                               Map<Boolean, List<TrimOptionQueryRepository.TrimOptionInfo>> collectedOptions) {
        List<TrimOptionListResponseDto.TrimOptionDto> selectableOptions = getOptions(collectedOptions.getOrDefault(false, new ArrayList<>()));
        List<TrimOptionListResponseDto.TrimOptionDto> selectablePackages = getOptions(packages);
        selectablePackages.addAll(selectableOptions);
        return selectablePackages;
    }

    private List<TrimOptionListResponseDto.TrimOptionDto> getOptions(List<TrimOptionQueryRepository.TrimOptionInfo> collectedOptions) {
        return collectedOptions.stream()
                .map(option -> new TrimOptionListResponseDto.TrimOptionDto(option, 0))
                .collect(Collectors.toList());
    }

    private List<HMGDataDto> buildHmgDataDtoList(List<TrimOptionQueryRepository.HMGDataInfo> hmgDataList) {
        return hmgDataList.stream()
                .map(hmgDataInfo -> HMGDataDto.builder()
                        .name(hmgDataInfo.getName())
                        .value(makeValue(hmgDataInfo.getVal(), hmgDataInfo.getUnit()))
                        .measure(hmgDataInfo.getMeasure())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    private String makeValue(String value, String unit) {
        return value + (!Objects.isNull(unit) ? unit : "");
    }
}
