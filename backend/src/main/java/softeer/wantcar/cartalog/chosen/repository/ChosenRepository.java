package softeer.wantcar.cartalog.chosen.repository;

import softeer.wantcar.cartalog.chosen.repository.dto.ChosenDto;

import java.util.List;

public interface ChosenRepository {
    List<ChosenDto> findModelTypeChosenByOptionId(List<String> modelTypeIds);

    List<ChosenDto> findOptionChosenByOptionId(List<String> optionIds);

    List<ChosenDto> findPackageChosenByOptionId(List<String> packageIds);

    List<ChosenDto> findExteriorColorChosenByExteriorColorCode(List<String> exteriorColorCodes);

    List<ChosenDto> findInteriorColorChosenByInteriorColorCode(List<String> interiorColorCodes);
}
