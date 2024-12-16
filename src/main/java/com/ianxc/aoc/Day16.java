package com.ianxc.aoc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Day16 {
    // A* search for fun.
    static long part1(String path) {
        // Parse
        final var grid = parseGrid(path);
        final var height = grid.length;
        final var width = grid[0].length;

        System.out.println(Arrays.deepToString(grid));

        // Initialise costs to visit
        var costToReach = new long[height][width][Direction.length];
        fill3d(costToReach, Long.MAX_VALUE / 2);

        // A* search
        final var cmp = Comparator.comparingLong(State::priorityCost);
        final var frontier = new PriorityQueue<>(cmp);
        frontier.offer(new State(height - 2, 1, Direction.EAST, 0));
        costToReach[height - 2][1][Direction.EAST.ordinal()] = 0;

        while (!frontier.isEmpty()) {
            var curr = frontier.poll();
            System.out.println("poll  " + curr);
            if (curr.i == 1 && curr.j == width - 2) return costToReach[curr.i][curr.j][curr.direction.ordinal()];

            for (var newDir : curr.direction.nextDirs()) {
                var newI = curr.i + newDir.di;
                var newJ = curr.j + newDir.dj;
                if (grid[newI][newJ] == '.') {
                    final var newCost = costToReach[curr.i][curr.j][curr.direction.ordinal()] + 1 + (newDir == curr.direction ? 0 : 1000);
                    if (newCost < costToReach[newI][newJ][newDir.ordinal()]) {
                        costToReach[newI][newJ][newDir.ordinal()] = newCost;
                        final var newPriority = newCost + heuristic(newI, newJ, height, width);
                        final var newState = new State(newI, newJ, newDir, newPriority);
                        frontier.offer(newState);
                    }
                }
            }
        }
        return -1;
    }

    // Manhattan distance admissible heuristic
    static long heuristic(int i, int j, int height, int width) {
        return Math.abs(height - 2 - i) + Math.abs(width - 2 - j);
    }

    static char[][] parseGrid(String path) {
        return Util.loadFile(path).stream().map(String::toCharArray).toArray(char[][]::new);
    }

    static void fill3d(long[][][] grid, @SuppressWarnings("SameParameterValue") long value) {
        for (var row : grid) {
            for (var subsection : row) {
                Arrays.fill(subsection, value);
            }
        }
    }

    static void printPath(Point[][] cameFrom, int height, int width) {
        var currI = 1;
        var currJ = width - 2;
        var count = 0;
        while (currI != height - 2 && currJ != 1) {
            System.out.printf("#%3d: i=%d, j=%d\n", count, currI, currJ);
            var p = cameFrom[currI][currJ];
            currI = p.i;
            currJ = p.j;
            count++;
        }
        System.out.printf("#%3d: i=%d, j=%d\n", count, currI, currJ);
    }

    enum Direction {
        EAST(0, 1),
        NORTH(-1, 0),
        WEST(0, -1),
        SOUTH(1, 0);

        static final Direction[] values = Direction.values();
        static final int length = values.length;
        final int di;
        final int dj;

        Direction(int di, int dj) {
            this.di = di;
            this.dj = dj;
        }

        Direction prev() {
            return Direction.values[Math.floorMod(this.ordinal() - 1, Direction.length)];
        }

        Direction next() {
            return Direction.values[(this.ordinal() + 1) % Direction.length];
        }

        List<Direction> nextDirs() {
            return List.of(this, this.prev(), this.next());
        }
    }

    record State(int i, int j, Direction direction, long priorityCost) {
    }

    record Point(int i, int j) {
    }
}