package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day05 {
    static Input parse(String path) {
        var deps = new ArrayList<Pair>();
        var updates = new ArrayList<List<Integer>>();
        var stage2 = false;
        for (var line : Util.loadFile(path)) {
            if ("".equals(line)) {
                stage2 = true;
                continue;
            }
            if (stage2) {
                var strs = line.split(",");
                var nums = Arrays.stream(strs).map(Integer::parseInt).toList();
                updates.add(nums);
            } else {
                var strs = line.split("\\|");
                var left = Integer.parseInt(strs[0]);
                var right = Integer.parseInt(strs[1]);
                deps.add(new Pair(left, right));
            }
        }
        return new Input(deps, updates);
    }

    static int part1(String path) {
        final var input = parse(path);
        final var deps = input.deps;
        final var updates = input.updates;

        var graph = new HashMap<Integer, List<Integer>>();
        for (var d : deps) {
            graph.putIfAbsent(d.left, new ArrayList<>());
            graph.putIfAbsent(d.right, new ArrayList<>());
            graph.get(d.left).add(d.right);
        }

        return updates.stream()
                .filter(seq -> isValid(graph, seq))
                .mapToInt(Day05::middleElement)
                .sum();
    }

    static int part2(String path) {
        final var input = parse(path);
        final var deps = input.deps;
        final var updates = input.updates;

        return 0;
    }

    static boolean isValid(Map<Integer, List<Integer>> graph, List<Integer> seq) {
        var allInSeq = Set.copyOf(seq);
        var unseenNeighbors = new HashSet<>();
        for (var num : seq) {
            unseenNeighbors.remove(num);
            var numsToSee = graph.get(num).stream().filter(allInSeq::contains).toList();
            unseenNeighbors.addAll(numsToSee);
        }
        return unseenNeighbors.isEmpty();
    }

    static int middleElement(List<Integer> seq) {
        return seq.get(seq.size() / 2);
    }

    record Pair(int left, int right) {
    }

    record Input(List<Pair> deps, List<List<Integer>> updates) {
    }
}