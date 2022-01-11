package de.libaurea.adventofcode.year2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.libaurea.adventofcode.common.Solution;

public class PassagePathing extends Solution {
    private static final String NODE_START = "start";
    private static final String NODE_END = "end";
    private Map<String, Set<String>> nodes;
    private List<String> visited;
    private Set<List<String>> paths;

    public void solve() {
        nodes = new HashMap<>();
        for (String string : input) {
            String[] path = string.split("-");
            for (int i = 0; i < path.length; i++) {
                if (nodes.containsKey(path[i])) {
                    nodes.get(path[i]).add(path[(i + 1) % 2]);
                } else {
                    Set<String> other = new HashSet<>();
                    other.add(path[(i + 1) % 2]);
                    nodes.put(path[i], other);
                }
            }
        }

        paths = new HashSet<>();
        visited = new ArrayList<>();
        visited.add(NODE_START);
        visit(NODE_START, NODE_END, "");
        LOGGER.info("Part 1: " + paths.size());

        Set<String> smallCaves = new HashSet<>();
        smallCaves.addAll(nodes.keySet().parallelStream()
                .filter(s -> s.equals(s.toLowerCase()))
                .filter(s -> !s.equals(NODE_START))
                .filter(s -> !s.equals(NODE_END))
                .toList());
        for (String twice : smallCaves) {
            visit(NODE_START, NODE_END, twice);
        }
        LOGGER.info("Part 2: " + paths.size());
    }

    private void visit(String current, String end, String twice) {
        for (String next : nodes.get(current)) {
            if (next.equals(end)) {
                List<String> path = new ArrayList<>();
                path.addAll(visited);
                paths.add(path);
            } else if (!next.equals(next.toLowerCase()) || !visited.contains(next) || twice(next, twice)) {
                visited.add(next);
                visit(next, end, twice);
                visited.remove(visited.size() - 1);
            }
        }
    }

    private boolean twice(String next, String twice) {
        return next.equals(twice) && visited.parallelStream().filter(s -> s.equals(next)).count() == 1;
    }

    public static void main(String... args) {
        LOGGER.info("=== Day 12 - Passage Pathing ===");
        LOGGER.info("--- Example ---");
        new PassagePathing().with("2021/day12-example.txt").solve();
        LOGGER.info("--- Task ---");
        new PassagePathing().with("2021/day12.txt").solve();
    }
}
