package com.ianxc.aoc;

import java.util.*;

public class Day05 {
    record Pair(int left, int right) {
    }


    static int part1(String path) {
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
//                System.out.println(nums);
            } else {
                var strs = line.split("\\|");
                var left = Integer.parseInt(strs[0]);
                var right = Integer.parseInt(strs[1]);
                deps.add(new Pair(left, right));
            }
        }

        System.out.println(deps);


        var graph = new HashMap<Integer, List<Integer>>();

        for (var d : deps) {
            graph.putIfAbsent(d.left, new ArrayList<>());
            graph.putIfAbsent(d.right, new ArrayList<>());
            graph.get(d.left).add(d.right);
        }

        System.out.println(graph);
        topologicalSort(graph);

        return 0;
    }

    private static void topologicalSort(Map<Integer, List<Integer>> graph) {
        var processed = new ArrayList<Integer>();
        var visited = new HashSet<Integer>();
        for (var node : graph.keySet()) {
            if (!visited.contains(node)) {
                traverseGraph(graph, visited, node, processed);
            }
        }
        Collections.reverse(processed);
        System.out.println("processed " + processed);
    }

    private static void traverseGraph(Map<Integer, List<Integer>> graph, Set<Integer> visited,
                                      int node, List<Integer> processed) {
        if (visited.contains(node)) {
            return;
        }

        visited.add(node);

        for (int neighbour : graph.get(node)) {
            traverseGraph(graph, visited, neighbour, processed);
        }

        processed.add(node);
    }
}
