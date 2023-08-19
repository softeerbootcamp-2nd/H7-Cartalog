package softeer.wantcar.cartalog.estimate.repository.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class HashTagMap {
    private final SortedMap<String, Long> hashTags;

    public HashTagMap(List<String> hashTags) {
        this(new TreeMap<>(hashTags.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))));
    }

    public HashTagMap(String hashTagKey) {
        SortedMap<String, Long> curHashTagMap = new TreeMap<>();
        for (String hashTag : hashTagKey.split("\\|")) {
            String[] keyAndValue = hashTag.split(":");
            curHashTagMap.put(keyAndValue[0], Long.parseLong(keyAndValue[1]));
        }
        hashTags = curHashTagMap;
    }

    public String getKey() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : hashTags.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append(":");
            stringBuilder.append(hashTags.get(key));
            stringBuilder.append("|");
        }
        return stringBuilder.toString();
    }

    public double getSimilarity(HashTagMap other) {
        SortedMap<String, Long> otherHashTags = other.getHashTags();
        long upperValue = getUpperValue(getHashTags(), otherHashTags, getAllKeys(getHashTags(), otherHashTags));
        double lowerValue = getHashTagVectorSize(getHashTags()) * getHashTagVectorSize(otherHashTags);

        if (lowerValue == 0) {
            return 0;
        }
        return 1 - (upperValue / lowerValue);
    }

    private static double getHashTagVectorSize(Map<String, Long> hashTagMap) {
        return Math.sqrt(hashTagMap.values().stream()
                .map(value -> Math.pow(value, 2))
                .mapToDouble(Double::valueOf)
                .sum());
    }

    private static long getUpperValue(Map<String, Long> hashTagMap, Map<String, Long> pendingHashTagMap, Set<String> totalKeys) {
        return totalKeys.stream()
                .map(key -> hashTagMap.getOrDefault(key, 0L) * pendingHashTagMap.getOrDefault(key, 0L))
                .mapToLong(Long::valueOf)
                .sum();
    }

    private static Set<String> getAllKeys(Map<String, Long> hashTagMap, Map<String, Long> pendingHashTagMap) {
        Set<String> totalHashTagKeys = new HashSet<>();
        totalHashTagKeys.addAll(hashTagMap.keySet());
        totalHashTagKeys.addAll(pendingHashTagMap.keySet());
        return totalHashTagKeys;
    }
}
