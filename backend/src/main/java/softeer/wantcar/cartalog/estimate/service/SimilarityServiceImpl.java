package softeer.wantcar.cartalog.estimate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateCountResponseDto;
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
import java.util.Objects;
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
    public SimilarEstimateResponseDto getSimilarEstimateInfo(Long estimateId, Long similarEstimateId) {
        List<EstimateInfoDto> similarEstimateInfos = estimateQueryRepository.findEstimateInfoBydEstimateIds(List.of(similarEstimateId));
        if (similarEstimateInfos.isEmpty()) {
            return null;
        }
        Map<Long, List<EstimateOptionInfoDto>> estimateOptionInfos = getEstimateOptionInfos(List.of(estimateId, similarEstimateId));

        return getSimilarEstimateResponseDto(estimateId, similarEstimateId, similarEstimateInfos, estimateOptionInfos);
    }

    private static SimilarEstimateResponseDto getSimilarEstimateResponseDto(Long estimateId,
                                                                            Long similarEstimateId,
                                                                            List<EstimateInfoDto> similarEstimateInfos,
                                                                            Map<Long, List<EstimateOptionInfoDto>> estimateOptionInfos) {
        EstimateInfoDto similarEstimateInfo = similarEstimateInfos.get(0);

        List<String> existOptionInfos = estimateOptionInfos.getOrDefault(estimateId, new ArrayList<>()).stream()
                .map(EstimateOptionInfoDto::getOptionId)
                .collect(Collectors.toList());
        List<EstimateOptionInfoDto> similarEstimateOptionInfos = estimateOptionInfos.getOrDefault(similarEstimateId, new ArrayList<>());

        return SimilarEstimateResponseDto.builder()
                .trimName(similarEstimateInfo.getTrimName())
                .price(getEstimatePrice(similarEstimateInfos, similarEstimateOptionInfos))
                .modelTypes(getModelTypes(similarEstimateInfos))
                .exteriorColorCode(similarEstimateInfo.getExteriorColorCode())
                .interiorColorCode(similarEstimateInfo.getInteriorColorCode())
                .nonExistentOptions(getNonExistOptionInfoDtoList(existOptionInfos, similarEstimateOptionInfos))
                .build();
    }

    @Override
    public SimilarEstimateCountResponseDto getSimilarEstimateCounts(Long estimateId) {
        EstimateOptionIdListDto estimateOptionIdListDto = estimateQueryRepository.findEstimateOptionIdsByEstimateId(estimateId);
        if (estimateOptionIdListDto == null) {
            return null;
        }
        List<String> totalHashTags = getTotalHashTags(estimateOptionIdListDto);
        List<Long> estimateIds = getSimilarEstimateIds(estimateOptionIdListDto.getTrimId(), totalHashTags);
        estimateIds.add(estimateId);

        List<EstimateCountDto> estimateCounts = estimateQueryRepository.findEstimateCounts(estimateIds);

        Long myEstimateCount = getMyEstimateCount(estimateId, estimateCounts);
        return SimilarEstimateCountResponseDto.builder()
                .myEstimateCount(myEstimateCount)
                .similarEstimateCounts(getSimilarEstimateCounts(estimateId, estimateCounts))
                .build();
    }

    private static Long getMyEstimateCount(Long estimateId, List<EstimateCountDto> estimateCounts) {
        return estimateCounts.stream()
                .filter(estimateCountDto -> Objects.equals(estimateCountDto.getEstimateId(), estimateId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new).getCount();
    }

    private static List<EstimateCountDto> getSimilarEstimateCounts(Long estimateId, List<EstimateCountDto> estimateCounts) {
        return estimateCounts.stream()
                .filter(estimateCountDto -> !Objects.equals(estimateCountDto.getEstimateId(), estimateId))
                .collect(Collectors.toList());
    }

    private List<String> getTotalHashTags(EstimateOptionIdListDto estimateOptionIdListDto) {
        List<String> totalHashTags = new ArrayList<>();
        totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromOptionsByOptionIds(estimateOptionIdListDto.getOptionIds()));
        totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromPackagesByPackageIds(estimateOptionIdListDto.getPackageIds()));
        return totalHashTags.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Long> getSimilarEstimateIds(Long trimId, List<String> hashTags) {
        PendingHashTagMap pendingHashTagMap = new PendingHashTagMap(hashTags);
        lazyCalculateHashTagKeys(trimId, pendingHashTagMap);
        List<String> similarHashTagKeys = similarityQueryRepository.findSimilarHashTagKeysByTrimIdAndHashTagKey(trimId, pendingHashTagMap.getKey());
        return similarityQueryRepository.findSimilarEstimateIdsByTrimIdAndHashTagKey(trimId, similarHashTagKeys);
    }

    private Map<Long, List<EstimateOptionInfoDto>> getEstimateOptionInfos(List<Long> similarEstimateIds) {
        List<EstimateOptionInfoDto> totalEstimateOptions = new ArrayList<>();
        totalEstimateOptions.addAll(estimateQueryRepository.findEstimateOptionsByEstimateIds(similarEstimateIds));
        totalEstimateOptions.addAll(estimateQueryRepository.findEstimatePackagesByEstimateIds(similarEstimateIds));
        return totalEstimateOptions.stream()
                .collect(Collectors.groupingBy(EstimateOptionInfoDto::getEstimateId));
    }

    private void lazyCalculateHashTagKeys(Long trimId, PendingHashTagMap pendingHashTagMap) {
        List<PendingHashTagMap> pendingPendingHashTagMaps =
                similarityQueryRepository.findPendingHashTagMapByTrimIdAndHashTagKey(trimId, pendingHashTagMap.getKey());
        if (pendingPendingHashTagMaps.isEmpty()) {
            return;
        }

        PendingHashTagSimilaritySaveDto pendingHashTagSimilaritySaveDto = PendingHashTagSimilaritySaveDto.builder()
                .trimId(trimId)
                .hashTagKey(pendingHashTagMap.getKey())
                .pendingHashTagLeftKeys(getPendingHashTagKeys(pendingPendingHashTagMaps))
                .build();

        similarityCommandRepository.deletePending(trimId, pendingHashTagMap.getKey());
        similarityCommandRepository.savePendingHashTagSimilarities(pendingHashTagSimilaritySaveDto);
        similarityCommandRepository.saveCalculatedHashTagKeys(trimId, pendingHashTagMap.getKey(), getSimilarityInfos(pendingHashTagMap, pendingPendingHashTagMaps));
    }

    private static List<String> getPendingHashTagKeys(List<PendingHashTagMap> pendingPendingHashTagMaps) {
        return pendingPendingHashTagMaps.stream()
                .map(PendingHashTagMap::getKey)
                .collect(Collectors.toList());
    }

    private static List<SimilarityInfo> getSimilarityInfos(PendingHashTagMap pendingHashTagMap, List<PendingHashTagMap> pendingPendingHashTagMaps) {
        return pendingPendingHashTagMaps.stream()
                .map(otherMap -> new SimilarityInfo(otherMap.getKey(), otherMap.getSimilarity(pendingHashTagMap)))
                .collect(Collectors.toList());
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

    private static int getEstimatePrice(List<EstimateInfoDto> estimateInfos,
                                        List<EstimateOptionInfoDto> estimateOptionInfos) {
        int totalPrice = estimateInfos.get(0).getTrimPrice();
        totalPrice += estimateInfos.stream()
                .mapToInt(EstimateInfoDto::getModelTypePrice)
                .sum();
        totalPrice += estimateOptionInfos.stream()
                .mapToInt(EstimateOptionInfoDto::getPrice)
                .sum();
        return totalPrice;
    }
}
