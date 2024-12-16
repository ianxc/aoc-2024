package com.ianxc.aoc;

import java.util.Arrays;

public class Day16 {
    static long part1(String path) {
        // Parse
        final var grid = parseGrid(path);
        final var height = grid.length;
        final var width = grid[0].length;

        // Initialise costs to visit
        var costToReach = new int[height][width];
        fill2d(costToReach, Integer.MAX_VALUE / 2);

        return 0L;
    }

    static char[][] parseGrid(String path) {
        return Util.loadFile(path).stream().map(String::toCharArray).toArray(char[][]::new);
    }

    static void fill2d(int[][] grid, int value) {
        for (var row : grid) {
            Arrays.fill(row, value);
        }
    }

    enum Direction {
        EAST(0, 1),
        NORTH(-1, 0),
        WEST(0, -1),
        SOUTH(1, 0);

        final int di;
        final int dj;

        Direction(int di, int dj) {
            this.di = di;
            this.dj = dj;
        }
    }
}
