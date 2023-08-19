package softeer.wantcar.cartalog.estimate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateDto;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.SimilarityQueryRepository;
import softeer.wantcar.cartalog.estimate.repository.dto.*;
import softeer.wantcar.cartalog.estimate.service.dto.PendingHashTagSimilaritySaveDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimilarityServiceImpl implements SimilarityService {
    private static final int DISPLAY_OPTION_MAX_SIZE = 2;
    private final SimilarityQueryRepository similarityQueryRepository;
    private final SimilarityCommandRepository similarityCommandRepository;
    private final EstimateQueryRepository estimateQueryRepository;
    private final ModelOptionQueryRepository modelOptionQueryRepository;

    @Override
    public SimilarEstimateResponseDto getSimilarEstimateDtoList(Long estimateId) {
        EstimateOptionListDto estimateOptionIds = estimateQueryRepository.findEstimateOptionIdsByEstimateId(estimateId);
        if (estimateOptionIds == null) {
            return null;
        }
        List<String> totalHashTags = getTotalHashTags(estimateOptionIds);

        List<Long> similarEstimateIds = getSimilarEstimateIds(estimateOptionIds.getTrimId(), totalHashTags);
        Map<Long, List<EstimateInfoDto>> estimateInfos = getEstimateInfos(similarEstimateIds);
        Map<Long, List<EstimateOptionInfoDto>> estimateOptionInfos = getEstimateOptionInfos(similarEstimateIds);

        return getSimilarEstimateResponseDto(estimateOptionIds.getAllOptionIds(), estimateInfos, estimateOptionInfos);
    }

    private List<String> getTotalHashTags(EstimateOptionListDto estimateOptionListDto) {
        List<String> totalHashTags = new ArrayList<>();
        totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromOptionsByOptionIds(estimateOptionListDto.getOptionIds()));
        totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromPackagesByPackageIds(estimateOptionListDto.getPackageIds()));
        return totalHashTags.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Long> getSimilarEstimateIds(Long trimId, List<String> hashTags) {
        HashTagMap hashTagMap = new HashTagMap(hashTags);
        lazyCalculateHashTagKeys(trimId, hashTagMap);
        List<String> similarHashTagKeys = similarityQueryRepository.findSimilarHashTagKeysByTrimIdAndHashTagKey(trimId, hashTagMap.getKey());
        return similarityQueryRepository.findSimilarEstimateIdsByTrimIdAndHashTagKey(trimId, similarHashTagKeys);
    }

    private Map<Long, List<EstimateOptionInfoDto>> getEstimateOptionInfos(List<Long> similarEstimateIds) {
        List<EstimateOptionInfoDto> totalEstimateOptions = new ArrayList<>();
        totalEstimateOptions.addAll(similarityQueryRepository.findSimilarEstimateOptionsByEstimateIds(similarEstimateIds));
        totalEstimateOptions.addAll(similarityQueryRepository.findSimilarEstimatePackagesByEstimateIds(similarEstimateIds));
        return totalEstimateOptions.stream()
                .collect(Collectors.groupingBy(EstimateOptionInfoDto::getEstimateId));
    }

    private Map<Long, List<EstimateInfoDto>> getEstimateInfos(List<Long> similarEstimateIds) {
        return similarityQueryRepository.findSimilarEstimateInfoBydEstimateIds(similarEstimateIds).stream()
                .collect(Collectors.groupingBy(EstimateInfoDto::getEstimateId));
    }

    private void lazyCalculateHashTagKeys(Long trimId, HashTagMap hashTagMap) {
        List<HashTagMap> pendingHashTagMaps =
                similarityQueryRepository.findPendingHashTagMapByTrimIdAndHashTagKey(trimId, hashTagMap.getKey());
        if (pendingHashTagMaps.isEmpty()) {
            return;
        }

        PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto = PendingHashTagSimilaritySaveDto.builder()
                .trimId(trimId)
                .hashTagKey(hashTagMap.getKey())
                .pendingHashTagLeftKeys(getPendingHashTagKeys(pendingHashTagMaps))
                .build();

        similarityCommandRepository.deletePending(trimId, hashTagMap.getKey());
        similarityCommandRepository.savePendingHashTagSimilarities(pendingHashTagSimilaritySaveDto);
        similarityCommandRepository.saveCalculatedHashTagKeys(trimId, hashTagMap.getKey(), getSimilarityInfos(hashTagMap, pendingHashTagMaps));
    }

    private static List<String> getPendingHashTagKeys(List<HashTagMap> pendingHashTagMaps) {
        return pendingHashTagMaps.stream()
                .map(HashTagMap::getKey)
                .collect(Collectors.toList());
    }

    private static List<SimilarityInfo> getSimilarityInfos(HashTagMap hashTagMap, List<HashTagMap> pendingHashTagMaps) {
        return pendingHashTagMaps.stream()
                .map(otherMap -> new SimilarityInfo(otherMap.getKey(), otherMap.getSimilarity(hashTagMap)))
                .collect(Collectors.toList());
    }

    private static SimilarEstimateResponseDto getSimilarEstimateResponseDto(List<String> optionIds,
                                                                            Map<Long, List<EstimateInfoDto>> estimateInfos,
                                                                            Map<Long, List<EstimateOptionInfoDto>> estimateOptionInfos) {
        List<SimilarEstimateDto> estimateDtoList = new ArrayList<>();
        for (Long estimateId : estimateInfos.keySet()) {
            SimilarEstimateDto estimateDto = getSimilarEstimateDto(optionIds, estimateInfos.get(estimateId), estimateOptionInfos.get(estimateId));
            estimateDtoList.add(estimateDto);
        }
        return SimilarEstimateResponseDto.builder()
                .similarEstimates(estimateDtoList)
                .build();
    }

    private static SimilarEstimateDto getSimilarEstimateDto(List<String> optionIds,
                                                            List<EstimateInfoDto> estimateInfos,
                                                            List<EstimateOptionInfoDto> estimateOptionInfos) {
        EstimateInfoDto curEstimateInfo = estimateInfos.get(0);
        return SimilarEstimateDto.builder()
                .detailTrimId(curEstimateInfo.getDetailTrimId())
                .trimName(curEstimateInfo.getTrimName())
                .price(getEstimatePrice(estimateInfos, estimateOptionInfos, curEstimateInfo))
                .modelTypes(getModelTypes(estimateInfos))
                .exteriorColorCode(curEstimateInfo.getExteriorColorCode())
                .interiorColorCode(curEstimateInfo.getInteriorColorCode())
                .nonExistentOptions(getNonExistOptionInfoDtoList(optionIds, estimateOptionInfos))
                .build();
    }

    private static List<String> getModelTypes(List<EstimateInfoDto> estimateInfos) {
        return estimateInfos.stream()
                .map(EstimateInfoDto::getModelTypeName)
                .collect(Collectors.toList());
    }

    private static List<EstimateOptionInfoDto> getNonExistOptionInfoDtoList(List<String> optionIds, List<EstimateOptionInfoDto> estimateOptionInfos) {
        return estimateOptionInfos.stream()
                .filter(estimateOptionInfo -> !optionIds.contains(estimateOptionInfo.getOptionId()))
                .limit(DISPLAY_OPTION_MAX_SIZE)
                .collect(Collectors.toList());
    }

    private static int getEstimatePrice(List<EstimateInfoDto> estimateInfos, List<EstimateOptionInfoDto> estimateOptionInfos, EstimateInfoDto curEstimateInfo) {
        int totalPrice = curEstimateInfo.getTrimPrice();
        totalPrice += estimateInfos.stream()
                .mapToInt(EstimateInfoDto::getModelTypePrice)
                .sum();
        totalPrice += estimateOptionInfos.stream()
                .mapToInt(EstimateOptionInfoDto::getPrice)
                .sum();
        return totalPrice;
    }
}
