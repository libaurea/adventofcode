package de.libaurea.adventofcode.year2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import de.libaurea.adventofcode.common.Solution;

public class Chiton extends Solution {
    private static final int[][] NEIGHBOUR = { { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 0 } };
    private static final int EXPAND = 5;
    private Point end;
    private Point start;
    private Map<Point, Integer> map;
    private Map<Point, Integer> result;
    private Map<Point, Point> previous;

    public void solve() {
        start = new Point(0, 0);
        end = new Point(input.size() - 1, input.get(0).length() - 1);

        map = new HashMap<>();
        result = new HashMap<>();
        previous = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                Point p = new Point(i, j);
                map.put(p, Integer.valueOf(input.get(i).charAt(j) + ""));
                result.put(p, Integer.MAX_VALUE);
            }
        }

        result.put(start, 0);
        visit();

        LOGGER.info("Part 1: " + result.get(end));

        for (int i = 0; i < input.size() * EXPAND; i++) {
            for (int j = 0; j < input.get(0).length() * EXPAND; j++) {
                Point current = new Point(i, j);
                if (!map.containsKey(current)) {
                    Point original = new Point(i % (end.x + 1), j % (end.y + 1));
                    int value = map.get(original) + (i / (end.x + 1)) + (j / (end.y + 1));
                    value = value > 9 ? value % 9 : value;
                    map.put(current, value);
                }
            }
        }

        end = new Point((input.size() * EXPAND) - 1, (input.get(0).length() * EXPAND) - 1);
        map.keySet().stream().forEach(p -> result.put(p, Integer.MAX_VALUE));
        result.put(start, 0);
        visit();

        LOGGER.info("Part 2: " + result.get(end));
    }

    public void visit() {
        Set<Point> visited = new HashSet<>();
        PriorityQueue<Point> queue = new PriorityQueue<>((a, b) -> Integer.compare(result.get(a), result.get(b)));
        queue.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            visited.add(current);

            for (Point next : neighbour(current)) {
                if (!visited.contains(next)) {
                    int score = result.get(current) + map.get(next);

                    if (score < result.get(next)) {
                        result.put(next, score);
                        previous.put(next, current);
                        queue.add(next);
                    }
                }
            }
        }
    }

    public Set<Point> neighbour(Point start) {
        return Arrays.stream(NEIGHBOUR)
                .map(n -> new Point(start.x + n[0], start.y + n[1]))
                .filter(n -> map.containsKey(n))
                .collect(Collectors.toSet());
    }

    public static void main(String... args) {
        LOGGER.info("=== Day 15 - Chiton ===");
        LOGGER.info("--- Example ---");
        new Chiton().with("2021/day15-example.txt").solve();
        LOGGER.info("--- Task ---");
        new Chiton().with("2021/day15.txt").solve();
        LOGGER.error("This task was solved together with Istannen!");
        LOGGER.warn("Cuteness overload detected! jannik, that was you! :3");
    }

    private record Point(Integer x, Integer y) {
    }
}
