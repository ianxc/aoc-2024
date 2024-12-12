package com.ianxc.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

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

    static ExploredRegion exploreRegionSides(char[][] grid, int i, int j, boolean[][] visited) {
        final var height = grid.length;
        final var width = grid[0].length;
        final var currChar = grid[i][j];
        var area = 0L;

        final var stack = new ArrayDeque<Point>();
        stack.push(new Point(i, j));
        visited[i][j] = true;
        // Store horizontally-spanning points (j), indexed by a 2x1 vertical block
        var fenceV = new HashMap<SingleAxisPair, List<Integer>>();
        // Store vertically-spanning points (i), indexed by a 1x2 horizontal block
        var fenceH = new HashMap<SingleAxisPair, List<Integer>>();

        while (!stack.isEmpty()) {
            final var curr = stack.pop();
            area++;
            for (final var offset : offsets) {
                final var newI = curr.i + offset[0];
                final var newJ = curr.j + offset[1];
                if (newI >= 0 && newJ >= 0) {
                    if (newI < height && newJ < width && grid[newI][newJ] == currChar) {
                        if (!visited[newI][newJ]) {
                            visited[newI][newJ] = true;
                            stack.push(new Point(newI, newJ));
                        }
                        continue;
                    }
                }
                if (curr.i != newI) {
                    // changed i
                    fenceV.computeIfAbsent(new SingleAxisPair(curr.i, newI), k -> new ArrayList<>())
                            .add(curr.j);
                } else {
                    // changed j
                    fenceH.computeIfAbsent(new SingleAxisPair(curr.j, newJ), k -> new ArrayList<>())
                            .add(curr.i);
                }
            }
        }

        var sides = Stream.concat(fenceV.values().stream(), fenceH.values().stream())
                .mapToLong(spanningPoints -> {
                    Collections.sort(spanningPoints);
                    var fence = 1;
                    for (int idx = 1; idx < spanningPoints.size(); idx++) {
                        if (spanningPoints.get(idx) - spanningPoints.get(idx - 1) != 1) {
                            fence += 1;
                        }
                    }
                    return fence;
                })
                .sum();


        return new ExploredRegion(currChar, area, sides);
    }

    record ExploredRegion(char ch, long area, long perimeter) {
    }

    record Point(int i, int j) {
    }

    record SingleAxisPair(int x, int newX) {
    }
}
