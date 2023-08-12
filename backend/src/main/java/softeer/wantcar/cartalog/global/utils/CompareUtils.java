package softeer.wantcar.cartalog.global.utils;

import java.util.HashSet;
import java.util.List;

public abstract class CompareUtils {
    public static boolean equalAndAllContain(List<String> expected, List<String> actual) {
        return expected.size() == actual.size() && new HashSet<>(expected).equals(new HashSet<>(actual));
    }
}
