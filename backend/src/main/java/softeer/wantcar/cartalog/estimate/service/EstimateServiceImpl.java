package softeer.wantcar.cartalog.estimate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.dto.EstimateResponseDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionIdListDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateShareInfoDto;
import softeer.wantcar.cartalog.estimate.repository.dto.PendingHashTagMap;
import softeer.wantcar.cartalog.estimate.repository.dto.SimilarityInfo;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;
import softeer.wantcar.cartalog.estimate.service.dto.PendingHashTagSimilaritySaveDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;
import softeer.wantcar.cartalog.trim.repository.dto.OptionPackageInfoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EstimateServiceImpl implements EstimateService {
    private final EstimateQueryRepository estimateQueryRepository;
    private final EstimateCommandRepository estimateCommandRepository;
    private final TrimColorQueryRepository trimColorQueryRepository;
    private final TrimQueryRepository trimQueryRepository;
    private final ModelOptionQueryRepository modelOptionQueryRepository;
    private final SimilarityCommandRepository similarityCommandRepository;
    private final SimilarityQueryRepository similarityQueryRepository;
    private final TrimOptionQueryRepository trimOptionQueryRepository;

    @Override
    public Long saveOrFindEstimateId(EstimateRequestDto estimateRequestDto) {
        List<Long> selectPackages = estimateRequestDto.getSelectPackageIds();
        List<Long> selectOptions = estimateRequestDto.getSelectOptionIds();

        Long trimId = trimQueryRepository.findTrimIdByDetailTrimId(estimateRequestDto.getDetailTrimId());
        EstimateDto estimateDto = buildEstimateDao(trimId, estimateRequestDto, selectPackages, selectOptions);

        Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(estimateDto);
        if (estimateId != null) {
            return estimateId;
        }

        try {
            estimateCommandRepository.save(estimateDto);
            PendingHashTagMap curPendingHashTagMap = new PendingHashTagMap(getTotalHashTags(estimateDto));
            List<String> calculatedHashTagKeys = similarityQueryRepository.findAllCalculatedHashTagKeys();
            registerPendingHashTagSimilarities(trimId, curPendingHashTagMap, calculatedHashTagKeys);
            saveHashTagSimilarities(trimId, curPendingHashTagMap, calculatedHashTagKeys);
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException();
        }

        return estimateQueryRepository.findEstimateIdByEstimateDto(estimateDto);
    }

    @Override
    public EstimateResponseDto findEstimateByEstimateId(Long estimateId) {
        EstimateShareInfoDto estimateShareInfo = estimateQueryRepository.findEstimateShareInfoByEstimateId(estimateId);
        if (estimateShareInfo == null) {
            throw new IllegalArgumentException();
        }

        List<Long> estimateModelTypeIds = estimateQueryRepository.findEstimateModelTypeIdsByEstimateId(estimateId);
        EstimateOptionIdListDto estimateOptionIds = estimateQueryRepository.findEstimateOptionIdsByEstimateId(estimateId);

        EstimateResponseDto.EstimateResponseDtoBuilder builder = EstimateResponseDto.builder()
                .trimId(estimateShareInfo.getTrimId())
                .detailTrimId(estimateShareInfo.getDetailTrimId())
                .displacement(estimateShareInfo.getDisplacement())
                .fuelEfficiency(estimateShareInfo.getFuelEfficiency())
                .exteriorColor(EstimateResponseDto.ColorDto.builder()
                        .colorCode(estimateShareInfo.getExteriorColorCode())
                        .imageUrl(estimateShareInfo.getExteriorColorImageUrl())
                        .name(estimateShareInfo.getExteriorColorName())
                        .price(estimateShareInfo.getExteriorColorPrice())
                        .build())
                .interiorColor(EstimateResponseDto.ColorDto.builder()
                        .colorCode(estimateShareInfo.getInteriorColorCode())
                        .imageUrl(estimateShareInfo.getInteriorCarImageUrl())
                        .name(estimateShareInfo.getInteriorColorName())
                        .price(estimateShareInfo.getInteriorColorPrice())
                        .build()
                )
                .exteriorCarDirectory(estimateShareInfo.getExteriorCarImageDirectory())
                .interiorCarImageUrl(estimateShareInfo.getInteriorColorImageUrl());


        List<OptionPackageInfoDto> modelTypesInfos =
                trimOptionQueryRepository.findOptionPackageInfoByOptionPackageIds(estimateModelTypeIds, null);

        List<OptionPackageInfoDto> selectOptionPackageInfos =
                trimOptionQueryRepository.findOptionPackageInfoByOptionPackageIds(estimateOptionIds.getOptionIds(), estimateOptionIds.getPackageIds());

        modelTypesInfos
                .forEach(infoDto -> builder.modelType(EstimateResponseDto.OptionPackageDto.builder()
                        .id("O" + infoDto.getId())
                        .childCategory(infoDto.getChildCategory())
                        .imageUrl(infoDto.getImageUrl())
                        .name(infoDto.getName())
                        .price(infoDto.getPrice())
                        .build())
                );


        selectOptionPackageInfos.stream()
                .filter(infoDto -> estimateOptionIds.getOptionIds().contains(infoDto.getId()))
                .forEach(infoDto -> builder.selectOptionOrPackage(EstimateResponseDto.OptionPackageDto.builder()
                        .id("O" + infoDto.getId())
                        .childCategory(infoDto.getChildCategory())
                        .imageUrl(infoDto.getImageUrl())
                        .name(infoDto.getName())
                        .price(infoDto.getPrice())
                        .build())
                );


        selectOptionPackageInfos.stream()
                .filter(infoDto -> estimateOptionIds.getPackageIds().contains(infoDto.getId()))
                .forEach(infoDto -> builder.selectOptionOrPackage(EstimateResponseDto.OptionPackageDto.builder()
                        .id("P" + infoDto.getId())
                        .childCategory(infoDto.getChildCategory())
                        .imageUrl(infoDto.getImageUrl())
                        .name(infoDto.getName())
                        .price(infoDto.getPrice())
                        .build())
                );

        return builder.build();
    }

    private void saveHashTagSimilarities(Long trimId, PendingHashTagMap curPendingHashTagMap, List<String> calculatedHashTagKeys) {
        List<SimilarityInfo> similarities = calculatedHashTagKeys.stream()
                .map(PendingHashTagMap::new)
                .map(otherMap -> new SimilarityInfo(otherMap.getKey(), otherMap.getSimilarity(curPendingHashTagMap)))
                .collect(Collectors.toList());
        similarityCommandRepository.saveCalculatedHashTagKeys(trimId, curPendingHashTagMap.getKey(), similarities);
    }

    private void registerPendingHashTagSimilarities(Long trimId, PendingHashTagMap curPendingHashTagMap, List<String> calculatedHashTagKeys) {
        PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto = PendingHashTagSimilaritySaveDto.builder()
                .trimId(trimId)
                .hashTagKey(curPendingHashTagMap.getKey())
                .pendingHashTagLeftKeys(calculatedHashTagKeys)
                .build();
        similarityCommandRepository.savePendingHashTagSimilarities(pendingHashTagSimilaritySaveDto);
    }

    private EstimateDto buildEstimateDao(Long trimId, EstimateRequestDto estimateRequestDto, List<Long> selectPackages, List<Long> selectOptions) {
        try {
            Long trimExteriorColorId = trimColorQueryRepository.findTrimExteriorColorIdByTrimIdAndColorCode(
                    trimId, estimateRequestDto.getExteriorColorCode());
            Long trimInteriorColorId = trimColorQueryRepository.findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(
                    trimId, estimateRequestDto.getExteriorColorCode(), estimateRequestDto.getInteriorColorCode());
            return EstimateDto.builder()
                    .detailTrimId(estimateRequestDto.getDetailTrimId())
                    .trimExteriorColorId(trimExteriorColorId)
                    .trimInteriorColorId(trimInteriorColorId)
                    .modelOptionIds(selectOptions)
                    .modelPackageIds(selectPackages)
                    .build();
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException();
        }
    }

    private List<String> getTotalHashTags(EstimateDto estimateDto) {
        List<String> totalHashTags = new ArrayList<>();
        totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromOptionsByOptionIds(estimateDto.getModelOptionIds()));
        totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromPackagesByPackageIds(estimateDto.getModelPackageIds()));
        return totalHashTags.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
