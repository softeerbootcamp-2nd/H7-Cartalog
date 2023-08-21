package softeer.wantcar.cartalog.chosen.repository;

import java.util.List;

public interface ChosenRepository {
    List<Integer> findModelTypeChosenByOptionId(List<Long> modelTypeIds, int daysAgo);

    List<Integer> findOptionChosenByOptionId(List<Long> optionIds, int daysAgo);

    List<Integer> findPackageChosenByOptionId(List<Long> packageIds, int daysAgo);

    List<Integer> findExteriorColorChosenByExteriorColorCode(List<String> exteriorColorCodes, int daysAgo);

    List<Integer> findInteriorColorChosenByInteriorColorCode(String exteriorColorCode, List<String> interiorColorCodes, int daysAgo);
}
