package softeer.wantcar.cartalog.estimate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateServiceImpl implements EstimateService {
    private final EstimateQueryRepository estimateQueryRepository;
    private final EstimateCommandRepository estimateCommandRepository;
    private final TrimColorQueryRepository trimColorQueryRepository;

    @Override
    public Long saveOrFindEstimateId(EstimateRequestDto estimateRequestDto) {
        Long estimateId = estimateQueryRepository.findEstimateIdByRequestDto(estimateRequestDto);
        if (estimateId != null) {
            return estimateId;
        }
        List<Long> selectPackages = estimateRequestDto.getSelectOptionOrPackageIds().stream()
                .filter(id -> id.charAt(0) == 'P')
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toUnmodifiableList());

        List<Long> selectOptions = estimateRequestDto.getSelectOptionOrPackageIds().stream()
                .filter(id -> id.charAt(0) == 'O')
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toUnmodifiableList());

        estimateCommandRepository.save(EstimateCommandRepository.EstimateSaveDto.builder()
                .detailTrimId(estimateRequestDto.getDetailTrimId())
                .trimExteriorColorId(trimColorQueryRepository.findTrimExteriorColorIdByTrimIdAndColorCode(estimateRequestDto.getDetailTrimId(), estimateRequestDto.getExteriorColorCode()))
                .trimInteriorColorId(trimColorQueryRepository.findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(estimateRequestDto.getDetailTrimId(), estimateRequestDto.getExteriorColorCode(), estimateRequestDto.getInteriorColorCode()))
                .modelOptionIds(selectOptions)
                .modelPackageIds(selectPackages)
                .build());

        return estimateQueryRepository.findEstimateIdByRequestDto(estimateRequestDto);
    }
}
