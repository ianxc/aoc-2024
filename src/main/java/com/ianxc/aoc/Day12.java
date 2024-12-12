package com.ianxc.aoc;

import java.util.ArrayDeque;

public class Day12 {
    private static final int[][] offsets = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    static long part1(String path) {
        final var grid = parseInput(path);
        final var height = grid.length;
        final var width = grid[0].length;
        var totalPrice = 0L;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!Character.isLowerCase(grid[i][j])) {
                    final var region = exploreRegion(grid, i, j);
                    // System.out.println(region);
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
        final var visitedChar = Character.toLowerCase(currChar);
        // System.out.printf("Explore region i=%d j=%d char=%c\n", i, j, currChar);
        var area = 0L;
        var perimeter = 0L;
        final var queue = new ArrayDeque<Point>();
        grid[i][j] = visitedChar;
        queue.offer(new Point(i, j));
        while (!queue.isEmpty()) {
            final var curr = queue.poll();
            // record statistics and mark as visited
            // Count edges that are external or adjacent to different characters
            area++;
            for (final var offset : offsets) {
                final var newI = curr.i + offset[0];
                final var newJ = curr.j + offset[1];
                // Count grid boundaries and different characters as perimeter
                if (newI < 0 || newI >= height || newJ < 0 || newJ >= width ||
                        (grid[newI][newJ] != currChar && grid[newI][newJ] != visitedChar)) {  // Removed the VISITED_MARKER check
                    // System.out.printf("i=%d j=%d offset=(%d, %d)\n", curr.i, curr.j, offset[0], offset[1]);
                    perimeter++;
                }
                // Add unvisited neighbors of same character to queue
                if (newI >= 0 && newI < height && newJ >= 0 && newJ < width &&
                        grid[newI][newJ] == currChar && grid[newI][newJ] != visitedChar) {
                    grid[newI][newJ] = visitedChar;
                    queue.offer(new Point(newI, newJ));
                }
            }
            // System.out.printf("area=%d perim=%d\n", area, perimeter);
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
