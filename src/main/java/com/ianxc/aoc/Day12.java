package com.ianxc.aoc;

import java.util.ArrayDeque;

public class Day12 {
    private static final char VISITED_MARKER = '.';

    private static final int[][] offsets = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    static long part1(String path) {
        final var grid = parseInput(path);
        final var height = grid.length;
        final var width = grid[0].length;
        var totalPrice = 0L;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] != VISITED_MARKER) {
                    final var region = exploreRegion(grid, i, j);
                    System.out.println(region);
                    totalPrice += region.area * region.perimeter;
                }
            }
        }
        return totalPrice;
    }

    static ExploredRegion exploreRegion(char[][] grid, int i, int j) {
        final var height = grid.length;
        final var width = grid[0].length;
        final var currChar = grid[i][j];
        System.out.printf("Explore region i=%d j=%d char=%c\n", i, j, currChar);
        var area = 0L;
        var perimeter = 0L;
        final var queue = new ArrayDeque<Point>();
        queue.offer(new Point(i, j));
        grid[i][j] = VISITED_MARKER;
        while (!queue.isEmpty()) {
            final var curr = queue.poll();
            // record statistics and mark as visited
            var perimeterContribution = 0L;
            for (final var offset : offsets) {
                final var newI = curr.i + offset[0];
                final var newJ = curr.j + offset[1];
                var inGridAndSameChar = newI >= 0 && newI < height && newJ >= 0 && newJ < width && grid[newI][newJ] == currChar;
                if (inGridAndSameChar) {
                    if (grid[newI][newJ] != VISITED_MARKER) {
                        grid[newI][newJ] = VISITED_MARKER;
                        queue.offer(new Point(newI, newJ));
                    }
                } else {
                    perimeterContribution++;
                }
            }
            area++;
            perimeter += perimeterContribution;
        }

        return new ExploredRegion(currChar, area, perimeter);
    }

    static char[][] parseInput(String path) {
        return Util.loadFile(path).stream().map(String::toCharArray).toArray(char[][]::new);
    }

    record ExploredRegion(char ch, long area, long perimeter) {
    }

    record Point(int i, int j) {
    }
}
