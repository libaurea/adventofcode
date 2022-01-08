package de.libaurea.adventofcode.year2021;

import de.libaurea.adventofcode.common.Solution;

public class Dive extends Solution {
    public void solve() {
        int horizontal = 0;
        int depthOrAim = 0;
        int depth2 = 0;
        for (String direction : input) {
            String[] current = direction.split(" ");
            switch (current[0]) {
                case "forward":
                    int range = Integer.parseInt(current[1]);
                    horizontal += range;
                    depth2 += depthOrAim * range;
                    break;
                case "down":
                    depthOrAim += Integer.parseInt(current[1]);
                    break;
                case "up":
                    depthOrAim -= Integer.parseInt(current[1]);
                    break;
                default:
                    break;
            }
        }
        LOGGER.info("Part 1: " + horizontal + ", " + depthOrAim + " (" + horizontal * depthOrAim + ")");
        LOGGER.info("Part 2: " + horizontal + ", " + depth2 + " (" + horizontal * depth2 + ")");
    }

    public static void main(String... args) {
        LOGGER.info("=== Day 2 - Dive! ===");
        LOGGER.info("--- Example ---");
        new Dive().with("2021/day2-example.txt").solve();
        LOGGER.info("--- Task ---");
        new Dive().with("2021/day2.txt").solve();
    }
}
