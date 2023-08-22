package softeer.wantcar.cartalog.chosen.repository;

import java.util.List;

public interface ChosenRepository {
    List<Integer> findModelTypeChosenByOptionId(List<String> modelTypeIds);

    List<Integer> findOptionChosenByOptionId(List<String> optionIds);

    List<Integer> findPackageChosenByOptionId(List<String> packageIds);

    List<Integer> findExteriorColorChosenByExteriorColorCode(List<String> exteriorColorCodes);

    List<Integer> findInteriorColorChosenByInteriorColorCode(String exteriorColorCode, List<String> interiorColorCodes);
}
