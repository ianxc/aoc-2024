package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Day08 {
    static int part1(String path) {
        var grid = Util.loadFile(path).stream().map(String::toCharArray).toArray(char[][]::new);
        var height = grid.length;
        var width = grid[0].length;

        var antennae = new HashMap<Character, List<Point>>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] != '.') {
                    antennae.computeIfAbsent(grid[i][j], k -> new ArrayList<>()).add(new Point(i, j));
                }
            }
        }

        return (int) antennae.entrySet()
                .stream()
                .flatMap(e ->
                        pairsOf(e.getValue())
                                .stream()
                                .flatMap(pair -> {
                                            var di = pair.right.i - pair.left.i;
                                            var dj = pair.right.j - pair.left.j;
                                            var righter = new Point(pair.right.i + di, pair.right.j + dj);
                                            var lefter = new Point(pair.left.i - di, pair.left.j - dj);
                                            return Stream.of(righter, lefter);
                                        }
                                )
                                .filter(point -> point.i >= 0 && point.i < height &&
                                        point.j >= 0 && point.j < width))
                .distinct()
                .count();
    }

    static <T> List<Pair<T>> pairsOf(List<T> items) {
        var n = items.size();
        var result = new ArrayList<Pair<T>>(n / 2 * n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                result.add(new Pair<>(items.get(i), items.get(j)));
            }
        }
        return result;
    }

    record Point(int i, int j) {
    }

    record Pair<T>(T left, T right) {
    }
}
