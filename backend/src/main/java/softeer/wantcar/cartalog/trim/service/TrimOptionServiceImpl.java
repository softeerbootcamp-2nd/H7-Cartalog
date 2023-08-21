package softeer.wantcar.cartalog.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.chosen.ChosenConfig;
import softeer.wantcar.cartalog.chosen.repository.ChosenRepository;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;
import softeer.wantcar.cartalog.trim.repository.dto.TrimOptionChosenInfo;
import softeer.wantcar.cartalog.trim.repository.dto.TrimOptionInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TrimOptionServiceImpl implements TrimOptionService {
    private final TrimOptionQueryRepository trimOptionQueryRepository;
    private final ChosenRepository chosenRepository;

    @Override
    public TrimOptionListResponseDto getTrimOptionList(Long detailTrimId, String interiorColorCode) {
        List<String> multipleSelectableCategories = trimOptionQueryRepository.findMultipleSelectableCategories();
        List<TrimOptionInfo> options = trimOptionQueryRepository.findOptionsByDetailTrimId(detailTrimId);
        List<TrimOptionInfo> packages = trimOptionQueryRepository.findPackagesByDetailTrimId(detailTrimId);
        if (Objects.isNull(options) || Objects.isNull(packages)) {
            return null;
        }

        Map<Boolean, List<TrimOptionInfo>> collectedOptions = options.stream()
                .filter(option -> filterColorCondition(interiorColorCode, option))
                .collect(Collectors.groupingBy(TrimOptionInfo::isBasic));

        List<TrimOptionChosenInfo> defaultOptions = collectedOptions.getOrDefault(true, List.of()).stream()
                .map(defaultOption -> TrimOptionChosenInfo.from(defaultOption, 100))
                .collect(Collectors.toUnmodifiableList());

        List<TrimOptionChosenInfo> selectOptions = getChosenThenTransformTrimOptionChosenInfo(collectedOptions.getOrDefault(false, List.of()), true);
        List<TrimOptionChosenInfo> packageOptions = getChosenThenTransformTrimOptionChosenInfo(packages, false);


        return TrimOptionListResponseDto.builder()
                .multipleSelectParentCategory(multipleSelectableCategories)
                .defaultOptions(getOptions(defaultOptions))
                .selectOptions(getSelectableOptions(packageOptions, selectOptions))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TrimOptionDetailResponseDto getTrimOptionDetail(Long optionId) {
        TrimOptionQueryRepository.ModelOptionInfo modelOption = trimOptionQueryRepository.findModelOptionInfoByOptionId(optionId);
        if (modelOption == null) {
            return null;
        }

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

        //TODO: batch를 통해 조회 연산을 최적화 할 수 있다.
        List<Long> modelOptionIds = trimOptionQueryRepository.findModelOptionIdsByPackageId(packageId);
        List<TrimOptionDetailResponseDto> trimOptionDetailResponseDtoList = modelOptionIds.stream()
                .map(this::getTrimOptionDetail)
                .collect(Collectors.toUnmodifiableList());

        return TrimPackageDetailResponseDto.builder()
                .name(detailTrimPackageInfos.getName())
                .imageUrl(detailTrimPackageInfos.getImageUrl())
                .hashTags(hashTags)
                .options(trimOptionDetailResponseDtoList)
                .build();
    }

    private boolean filterColorCondition(String interiorColorCode, TrimOptionInfo option) {
        return !option.isColorCondition() || option.getTrimInteriorColorIds().contains(interiorColorCode);
    }

    private List<TrimOptionChosenInfo> getChosenThenTransformTrimOptionChosenInfo(List<TrimOptionInfo> optionPackageInfo, boolean isOption) {
        List<Long> ids = optionPackageInfo.stream()
                .map(TrimOptionInfo::getId)
                .map(id -> id.substring(1))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
        List<Integer> chosen = isOption
                ? chosenRepository.findOptionChosenByOptionId(ids, ChosenConfig.CHOSEN_DAYS)
                : chosenRepository.findPackageChosenByOptionId(ids, ChosenConfig.CHOSEN_DAYS);
        return IntStream.range(0, optionPackageInfo.size())
                .mapToObj(index -> TrimOptionChosenInfo.from(optionPackageInfo.get(index), chosen.get(index)))
                .collect(Collectors.toUnmodifiableList());
    }

    private List<TrimOptionListResponseDto.TrimOptionDto> getSelectableOptions(List<TrimOptionChosenInfo> packages,
                                                                               List<TrimOptionChosenInfo> selectOptions) {
        List<TrimOptionListResponseDto.TrimOptionDto> selectableOptions = getOptions(selectOptions);
        List<TrimOptionListResponseDto.TrimOptionDto> selectablePackages = getOptions(packages);
        selectablePackages.addAll(selectableOptions);
        return selectablePackages;
    }

    private List<TrimOptionListResponseDto.TrimOptionDto> getOptions(List<TrimOptionChosenInfo> collectedOptions) {
        return collectedOptions.stream()
                .map(TrimOptionListResponseDto.TrimOptionDto::new)
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
