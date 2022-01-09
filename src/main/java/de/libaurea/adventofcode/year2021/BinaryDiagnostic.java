package de.libaurea.adventofcode.year2021;

import java.util.ArrayList;
import java.util.List;

import de.libaurea.adventofcode.common.Solution;

public class BinaryDiagnostic extends Solution {
    public void solve() {
        String gamma = common(count(input), input.size(), '0', '1');
        String epsilon = common(count(input), input.size(), '1', '0');
        Integer result = Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
        LOGGER.info("Part 1: " + gamma + " * " + epsilon + " = " + result.toString());

        String oxygen = criteria(input, '0', '1');
        String co2 = criteria(input, '1', '0');
        result = Integer.parseInt(oxygen, 2) * Integer.parseInt(co2, 2);
        LOGGER.info("Part 2: " + oxygen + " * " + co2 + " = " + result.toString());
    }

    private static String criteria(List<String> list, char standard, char other) {
        List<String> result = new ArrayList<>(list);
        for (int i = 0; i < list.get(0).length(); i++) {
            char keep = count(result)[i] > result.size() / 2 ? standard : other;
            result = filter(result, i, keep);
            if (result.size() == 1) {
                break;
            }
        }
        return result.get(0);
    }

    private static List<String> filter(List<String> list, int index, char keep) {
        return list.stream().filter(e -> e.charAt(index) == keep).toList();
    }

    private static int[] count(List<String> list) {
        int[] count = new int[list.get(0).length()];
        for (String string : list) {
            for (int i = 0; i < count.length; i++) {
                if (string.charAt(i) == '0') {
                    count[i]++;
                }
            }
        }
        return count;
    }

    private static String common(int[] count, int size, char standard, char other) {
        StringBuilder result = new StringBuilder();
        for (int i : count) {
            if (i > size / 2) {
                result.append(standard);
            } else {
                result.append(other);
            }
        }
        return result.toString();
    }

    public static void main(String... args) {
        LOGGER.info("=== Day 3 - Binary Diagnostic ===");
        LOGGER.info("--- Example ---");
        new BinaryDiagnostic().with("2021/day3-example.txt").solve();
        LOGGER.info("--- Task ---");
        new BinaryDiagnostic().with("2021/day3.txt").solve();
    }
}
