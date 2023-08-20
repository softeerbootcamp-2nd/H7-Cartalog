package softeer.wantcar.cartalog.estimate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.service.dto.EstimateDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;

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
    private final SimilarityService similarityService;

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
            List<String> hashTags = getTotalHashTags(estimateDto);
            similarityService.updateHashTagSimilarities(trimId, hashTags);
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException();
        }

        return estimateQueryRepository.findEstimateIdByEstimateDto(estimateDto);
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
