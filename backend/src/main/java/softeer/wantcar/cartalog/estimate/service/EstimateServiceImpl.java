package softeer.wantcar.cartalog.estimate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.dao.EstimateDao;
import softeer.wantcar.cartalog.estimate.repository.EstimateCommandRepository;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;

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

    @Override
    public Long saveOrFindEstimateId(EstimateRequestDto estimateRequestDto) {
        List<Long> selectPackages = estimateRequestDto.getSelectOptionOrPackageIds().stream()
                .filter(id -> id.charAt(0) == 'P')
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toUnmodifiableList());

        List<Long> selectOptions = estimateRequestDto.getSelectOptionOrPackageIds().stream()
                .filter(id -> id.charAt(0) == 'O')
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toUnmodifiableList());

        EstimateDao estimateDao;
        try {
            Long trimId = trimQueryRepository.findTrimIdByDetailTrimId(estimateRequestDto.getDetailTrimId());
            Long trimExteriorColorId = trimColorQueryRepository.findTrimExteriorColorIdByTrimIdAndColorCode(trimId, estimateRequestDto.getExteriorColorCode());
            Long trimInteriorColorId = trimColorQueryRepository.findTrimInteriorColorIdByTrimIdAndExteriorColorCodeAndInteriorColorCode(trimId, estimateRequestDto.getExteriorColorCode(), estimateRequestDto.getInteriorColorCode());
            estimateDao = EstimateDao.builder()
                    .detailTrimId(estimateRequestDto.getDetailTrimId())
                    .trimExteriorColorId(trimExteriorColorId)
                    .trimInteriorColorId(trimInteriorColorId)
                    .modelOptionIds(selectOptions)
                    .modelPackageIds(selectPackages)
                    .build();

        } catch (DataAccessException exception) {
            throw new IllegalArgumentException();
        }

        Long estimateId = estimateQueryRepository.findEstimateIdByRequestDto(estimateDao);
        if (estimateId != null) {
            return estimateId;
        }

        try {
            estimateCommandRepository.save(estimateDao);
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException();
        }

        return estimateQueryRepository.findEstimateIdByRequestDto(estimateDao);
    }
}
