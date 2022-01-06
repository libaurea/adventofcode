package de.libaurea.adventofcode.year2021;

import java.util.ArrayList;
import java.util.List;

import de.libaurea.adventofcode.common.Solution;

public class SonarSweep extends Solution {
    public void solve() {
        List<Integer> sweep = toIntegerList(input);
        LOGGER.info("Part 1: " + increases(sweep));
        LOGGER.info("Part 2: " + increases(windows(sweep)));
    }

    private static int increases(List<Integer> sweep) {
        int result = 0;
        for (int i = 1; i < sweep.size(); i++) {
            if (sweep.get(i - 1) < sweep.get(i)) {
                result++;
            }
        }
        return result;
    }

    private static List<Integer> windows(List<Integer> sweep) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < sweep.size() - 2; i++) {
            result.add(i, sweep.get(i) + sweep.get(i + 1) + sweep.get(i + 2));
        }
        return result;
    }

    public static void main(String... args) {
        LOGGER.info("=== Day 1 - Sonar Sweep ===");
        LOGGER.info("--- Example ---");
        new SonarSweep().with("2021/day1-example.txt").solve();
        LOGGER.info("--- Task ---");
        new SonarSweep().with("2021/day1.txt").solve();
    }
}
