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
                    final var region = exploreRegionPerimeter(grid, i, j);
                    // System.out.println(region);
                    totalPrice += region.area * region.perimeter;
                }
            }
        }
        return totalPrice;
    }

    static char[][] parseInput(String path) {
        return Util.loadFile(path).stream().map(String::toCharArray).toArray(char[][]::new);
    }

    static ExploredRegion exploreRegionPerimeter(char[][] grid, int i, int j) {
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
                final var inBounds = newI >= 0 && newI < height && newJ >= 0 && newJ < width;
                // Count grid boundaries and different characters as perimeter
                if (!inBounds || (grid[newI][newJ] != currChar && grid[newI][newJ] != visitedChar)) {  // Removed the VISITED_MARKER check
                    // System.out.printf("i=%d j=%d offset=(%d, %d)\n", curr.i, curr.j, offset[0], offset[1]);
                    perimeter++;
                }
                // Add unvisited neighbors of same character to queue
                if (inBounds && grid[newI][newJ] == currChar && grid[newI][newJ] != visitedChar) {
                    grid[newI][newJ] = visitedChar;
                    queue.offer(new Point(newI, newJ));
                }
            }
            // System.out.printf("area=%d perim=%d\n", area, perimeter);
        }

        return new ExploredRegion(currChar, area, perimeter);
    }

    static long part2(String path) {
        final var grid = parseInput(path);
        final var height = grid.length;
        final var width = grid[0].length;
        final var visited = new boolean[height][width];
        var totalPrice = 0L;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!visited[i][j]) {
                    final var region = exploreRegionSides(grid, i, j, visited);
                    System.out.println(region);
                    totalPrice += region.area * region.perimeter;
                }
            }
        }
        return totalPrice;
    }

    @SuppressWarnings("DuplicatedCode")
    static ExploredRegion exploreRegionSides(char[][] grid, int i, int j, boolean[][] visited) {
        final var height = grid.length;
        final var width = grid[0].length;
        final var currChar = grid[i][j];
        var area = 0L;
        var corners = 0L;

        final var queue = new ArrayDeque<Point>();
        visited[i][j] = true;
        queue.offer(new Point(i, j));

        while (!queue.isEmpty()) {
            final var curr = queue.poll();
            area++;

            for (int idx = 0; idx < offsets.length; idx++) {
                final var o1 = offsets[idx];
                final var c1i = curr.i + o1[0];
                final var c1j = curr.j + o1[1];
                final var c1Match = c1i >= 0 && c1i < height && c1j >= 0 && c1j < width && grid[c1i][c1j] == currChar;

                final var o2 = offsets[(idx + 1) % offsets.length];
                final var c2i = curr.i + o2[0];
                final var c2j = curr.j + o2[1];
                final var c2Match = c2i >= 0 && c2i < height && c2j >= 0 && c2j < width && grid[c2i][c2j] == currChar;

                final var cornerI = curr.i + o1[0] + o2[0];
                final var cornerJ = curr.j + o1[1] + o2[1];
                final var cornerMatch = cornerI >= 0 && cornerI < height && cornerJ >= 0 && cornerJ < width && grid[cornerI][cornerJ] == currChar;

                final var isExternalCorner = !c1Match && !c2Match;
                final var isInternalCorner = c1Match && c2Match && !cornerMatch;
                if (isExternalCorner || isInternalCorner) corners++;
            }

            for (final var offset : offsets) {
                final var newI = curr.i + offset[0];
                final var newJ = curr.j + offset[1];
                final var inBounds = newI >= 0 && newI < height && newJ >= 0 && newJ < width;
                if (inBounds && grid[newI][newJ] == currChar && !visited[newI][newJ]) {
                    visited[newI][newJ] = true;
                    queue.offer(new Point(newI, newJ));
                }
            }
        }

        return new ExploredRegion(currChar, area, corners);
    }

    record ExploredRegion(char ch, long area, long perimeter) {
    }

    record Point(int i, int j) {
    }
}
