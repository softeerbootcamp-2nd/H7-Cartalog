package softeer.wantcar.cartalog.similarity.repository.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class PendingHashTagMap {
    private final long idx;
    private final SortedMap<String, Long> hashTags;

    public PendingHashTagMap(long idx, String hashTagKey) {
        this.idx = idx;
        hashTags = getHashTagMap(hashTagKey);
    }

    public static String getHashTagKey(List<String> hashTags) {
        return getHashTagKey(new TreeMap<>(hashTags.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))));
    }

    private static String getHashTagKey(SortedMap<String, Long> hashTagMap) {
        return hashTagMap.keySet().stream()
                .map(key -> key + ":" + hashTagMap.get(key))
                .collect(Collectors.joining("?"));
    }

    public double getSimilarity(String otherHashTagKey) {
        SortedMap<String, Long> otherHashTags = getHashTagMap(otherHashTagKey);
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

    private static SortedMap<String, Long> getHashTagMap(String hashTagKey) {
        SortedMap<String, Long> curHashTagMap = new TreeMap<>();
        for (String hashTag : hashTagKey.split("\\?")) {
            String[] keyAndValue = hashTag.split(":");
            curHashTagMap.put(keyAndValue[0], Long.parseLong(keyAndValue[1]));
        }
        return curHashTagMap;
    }
}
