package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SuppressWarnings({"SameParameterValue", "DuplicatedCode"})
public class Day01 {
    static int part1(String path) {
        final var list1 = new ArrayList<Integer>(1_000_000);
        final var list2 = new ArrayList<Integer>(1_000_000);
        for (var line : Util.loadFile(path)) {
            var strings = line.split("\\s+");
            if (strings.length != 0) {
                list1.add(Integer.parseInt(strings[0]));
                list2.add(Integer.parseInt(strings[1]));
            }
        }

        Collections.sort(list1);
        Collections.sort(list2);

        for (int i = 0; i < list1.size(); i++) {
            list1.set(i, Math.abs(list1.get(i) - list2.get(i)));
        }

        return list1.stream().mapToInt(Integer::intValue).sum();
    }

    static int part2(String path) {
        final var list1 = new ArrayList<Integer>(1_000_000);
        final var list2 = new ArrayList<Integer>(1_000_000);
        for (var line : Util.loadFile(path)) {
            var strings = line.split("\\s+");
            if (strings.length != 0) {
                list1.add(Integer.parseInt(strings[0]));
                list2.add(Integer.parseInt(strings[1]));
            }
        }

        final var counts = new HashMap<Integer, Integer>();
        for (var n : list2) {
            counts.merge(n, 1, Integer::sum);
        }

        var similarity = 0;
        for (var n : list1) {
            similarity += n * counts.getOrDefault(n, 0);
        }

        return similarity;
    }
}
