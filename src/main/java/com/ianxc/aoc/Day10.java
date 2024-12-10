package com.ianxc.aoc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("DuplicatedCode")
public class Day10 {
    static final int[][] offsets = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    static int part1(String path) {
        var grid = parseGrid(path);
        var totalScore = 0;
        var memo = new HashMap<Point, Integer>();
        var height = grid.length;
        var width = grid[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 0) {
                    // reset visited for each trailhead we start from
                    var visited = new HashSet<Point>();
                    final var trailheadScore = dfs(memo, visited, i, j, 0, grid);
                    totalScore += trailheadScore;
                }
            }
        }
        return totalScore;
    }

    static int dfs(Map<Point, Integer> memo, Set<Point> visited, int i, int j, int currentValue, int[][] grid) {
        System.out.printf("enter      i=%d, j=%d, cv=%d, memo=%s\n", i, j, currentValue, memo);
        var currPoint = new Point(i, j);
        if (!visited.add(currPoint)) {
            return 0;
        }

        var countOrNull = memo.get(currPoint);
        if (countOrNull != null) {
            System.out.printf("exit  MEMO countOrNull=%d\n", countOrNull);
            return countOrNull;
        }

        if (currentValue == 9) {
//            memo.put(currPoint, 1);
            System.out.printf("exit  DONE i=%d, j=%d\n", i, j);
            return 1;
        }

        final var height = grid.length;
        final var width = grid[0].length;
        var currPointScore = 0;
        for (var offset : offsets) {
            var newI = i + offset[0];
            var newJ = j + offset[1];
            if (newI >= 0 && newI < height && newJ >= 0 && newJ < width && grid[newI][newJ] == currentValue + 1) {
                currPointScore += dfs(memo, visited, newI, newJ, currentValue + 1, grid);
            }
        }
// buggy because we may not visit in a consistent order perhaps?
//        memo.put(currPoint, currPointScore);
        System.out.printf("exit  PUT  i=%d, j=%d, cv=%d, currentPointScore=%d\n", i, j, currentValue, currPointScore);
        return currPointScore;
    }

    static int[][] parseGrid(String path) {
        var input = Util.loadFile(path);
        return input.stream()
                .map(str -> str.chars()
                        .map(i -> i == (int) '.' ? -1 : i - '0')
                        .toArray())
                .toArray(int[][]::new);
    }

    static int part2(String path) {
        var grid = parseGrid(path);
        var totalRating = 0;
        var memo = new HashMap<Point, Integer>();
        var height = grid.length;
        var width = grid[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 0) {
                    // reset visited for each trailhead we start from
                    final var trailheadRating = dfs2(memo, i, j, 0, grid);
                    totalRating += trailheadRating;
                }
            }
        }
        return totalRating;
    }

    static int dfs2(Map<Point, Integer> memo, int i, int j, int currentValue, int[][] grid) {
//        System.out.printf("enter      i=%d, j=%d, cv=%d, memo=%s\n", i, j, currentValue, memo);
        var currPoint = new Point(i, j);

        var countOrNull = memo.get(currPoint);
        if (countOrNull != null) {
//            System.out.printf("exit  MEMO countOrNull=%d\n", countOrNull);
            return countOrNull;
        }

        if (currentValue == 9) {
            memo.put(currPoint, 1);
//            System.out.printf("exit  DONE i=%d, j=%d\n", i, j);
            return 1;
        }

        final var height = grid.length;
        final var width = grid[0].length;
        var currPointScore = 0;
        for (var offset : offsets) {
            var newI = i + offset[0];
            var newJ = j + offset[1];
            if (newI >= 0 && newI < height && newJ >= 0 && newJ < width && grid[newI][newJ] == currentValue + 1) {
                currPointScore += dfs2(memo, newI, newJ, currentValue + 1, grid);
            }
        }
        memo.put(currPoint, currPointScore);
        return currPointScore;
    }

    record Point(int i, int j) {
    }
}
