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
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateOptionIdListDto;
import softeer.wantcar.cartalog.estimate.repository.dto.EstimateShareInfoDto;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.similarity.repository.SimilarityCommandRepository;
import softeer.wantcar.cartalog.similarity.repository.SimilarityQueryRepository;
import softeer.wantcar.cartalog.similarity.repository.dto.PendingHashTagMap;
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
@Transactional(readOnly = true)
public class EstimateServiceImpl implements EstimateService {
    private final EstimateQueryRepository estimateQueryRepository;
    private final EstimateCommandRepository estimateCommandRepository;
    private final TrimColorQueryRepository trimColorQueryRepository;
    private final TrimQueryRepository trimQueryRepository;
    private final ModelOptionQueryRepository modelOptionQueryRepository;
    private final SimilarityQueryRepository similarityQueryRepository;
    private final SimilarityCommandRepository similarityCommandRepository;
    private final TrimOptionQueryRepository trimOptionQueryRepository;

    @Override
    @Transactional
    public Long saveOrFindEstimateId(EstimateRequestDto estimateRequestDto) {
        List<Long> selectPackages = estimateRequestDto.getSelectPackageIds();
        List<Long> selectOptions = estimateRequestDto.getSelectOptionIds();

        Long trimId = trimQueryRepository.findTrimIdByDetailTrimId(estimateRequestDto.getDetailTrimId());
        EstimateDto estimateDto = buildEstimateDao(trimId, estimateRequestDto, selectPackages, selectOptions);

        Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(estimateDto);
        if (estimateId != null) {
            return estimateId;
        }

        return saveEstimate(trimId, estimateDto);
    }

    private synchronized Long saveEstimate(Long trimId, EstimateDto estimateDto) {
        Long estimateId = estimateQueryRepository.findEstimateIdByEstimateDto(estimateDto);
        if (estimateId != null) {
            return estimateId;
        }

        try {
            return saveEstimate(trimId, estimateDto, estimateQueryRepository.getEstimatePrice(estimateDto));
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public EstimateResponseDto findEstimateByEstimateId(Long estimateId) {
        EstimateShareInfoDto estimateShareInfo = estimateQueryRepository.findEstimateShareInfoByEstimateId(estimateId);
        if (estimateShareInfo == null) {
            throw new IllegalArgumentException();
        }

        List<Long> estimateModelTypeIds = estimateQueryRepository.findEstimateModelTypeIdsByEstimateId(estimateId);
        EstimateOptionIdListDto estimateOptionIds = estimateQueryRepository.findEstimateOptionIdsByEstimateId(estimateId);

        return getEstimateResponseDto(estimateShareInfo, estimateModelTypeIds, estimateOptionIds);
    }

    private EstimateResponseDto getEstimateResponseDto(EstimateShareInfoDto estimateShareInfo, List<Long> estimateModelTypeIds, EstimateOptionIdListDto estimateOptionIds) {
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
                .exteriorCarSideImageUrl(estimateShareInfo.getExteriorCarSideImageUrl())
                .interiorCarImageUrl(estimateShareInfo.getInteriorColorImageUrl());

        List<OptionPackageInfoDto> modelTypesInfos =
                trimOptionQueryRepository.findOptionPackageInfoByOptionPackageIds(estimateModelTypeIds, new ArrayList<>());

        List<OptionPackageInfoDto> selectOptionPackageInfos =
                trimOptionQueryRepository.findOptionPackageInfoByOptionPackageIds(estimateOptionIds.getOptionIds(), estimateOptionIds.getPackageIds());

        addOptionPackageInfoDto(builder, modelTypesInfos);
        addEstimateResponseOptionDto(builder, selectOptionPackageInfos, estimateOptionIds.getOptionIds(), true);
        addEstimateResponseOptionDto(builder, selectOptionPackageInfos, estimateOptionIds.getPackageIds(), false);

        return builder.build();
    }

    private static void addOptionPackageInfoDto(EstimateResponseDto.EstimateResponseDtoBuilder builder,
                                                List<OptionPackageInfoDto> optionPackageInfoDtoList) {
        optionPackageInfoDtoList
                .forEach(infoDto -> builder.modelType(EstimateResponseDto.OptionPackageDto.builder()
                        .id("O" + infoDto.getId())
                        .childCategory(infoDto.getChildCategory())
                        .imageUrl(infoDto.getImageUrl())
                        .name(infoDto.getName())
                        .price(infoDto.getPrice())
                        .build())
                );
    }

    private static void addEstimateResponseOptionDto(EstimateResponseDto.EstimateResponseDtoBuilder builder,
                                                     List<OptionPackageInfoDto> selectOptionPackageInfos,
                                                     List<Long> estimateOptionIds,
                                                     boolean isOption) {
        String prefix = isOption ? "O" : "P";
        selectOptionPackageInfos.stream()
                .filter(infoDto -> estimateOptionIds.contains(infoDto.getId()))
                .forEach(infoDto -> builder.selectOptionOrPackage(EstimateResponseDto.OptionPackageDto.builder()
                        .id(prefix + infoDto.getId())
                        .childCategory(infoDto.getChildCategory())
                        .imageUrl(infoDto.getImageUrl())
                        .name(infoDto.getName())
                        .price(infoDto.getPrice())
                        .build())
                );
    }

    private Long saveEstimate(Long trimId, EstimateDto estimateDto, int price) {
        Long estimateId = estimateCommandRepository.save(estimateDto, price);
        String hashTagKey = PendingHashTagMap.getHashTagKey(getTotalHashTags(estimateDto));
        if (!similarityQueryRepository.existHashTagKey(trimId, hashTagKey)) {
            similarityCommandRepository.saveHashTagKey(trimId, hashTagKey, 0);
            similarityCommandRepository.saveSimilarEstimate(trimId, hashTagKey, estimateId);
        }
        return estimateId;
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
        List<Long> modelOptionIds = estimateDto.getModelOptionIds();
        if (!modelOptionIds.isEmpty()) {
            totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromOptionsByOptionIds(modelOptionIds));
        }
        List<Long> modelPackageIds = estimateDto.getModelPackageIds();
        if (!modelPackageIds.isEmpty()) {
            totalHashTags.addAll(modelOptionQueryRepository.findHashTagFromPackagesByPackageIds(modelPackageIds));
        }
        return totalHashTags.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
