package com.ianxc.aoc;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

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

@SuppressWarnings("DuplicatedCode")
public class Day16 {
    public static final long UNSEEN_VAL = Long.MAX_VALUE / 2;

    // A* search for fun.
    static long part1(String path) {
        // Parse
        final var grid = parseGrid(path);
        final var height = grid.length;
        final var width = grid[0].length;

        // Initialise costs to visit
        // No need for 3d array where 3rd dimension is direction in this case.
        var costToReach = new long[height][width];
        fill2d(costToReach, UNSEEN_VAL);

        // A* search
        final var cmp = Comparator.comparingLong(State::priorityCost);
        final var frontier = new PriorityQueue<>(cmp);
        frontier.offer(new State(height - 2, 1, Direction.EAST, 0));
        costToReach[height - 2][1] = 0;

        while (!frontier.isEmpty()) {
            var curr = frontier.poll();
            if (curr.i == 1 && curr.j == width - 2) return costToReach[curr.i][curr.j];

            for (var newDir : curr.direction.nextDirs()) {
                var newI = curr.i + newDir.di;
                var newJ = curr.j + newDir.dj;
                // Lol, just had to check for E
                if (grid[newI][newJ] != '#') {
                    final var newCost = costToReach[curr.i][curr.j] + 1 + (newDir == curr.direction ? 0 : 1000);
                    if (newCost < costToReach[newI][newJ]) {
                        costToReach[newI][newJ] = newCost;
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

    static void fill2d(long[][] grid, @SuppressWarnings("SameParameterValue") long value) {
        for (var row : grid) {
            Arrays.fill(row, value);
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

    static long part2(String path) {
        // Parse
        final var grid = parseGrid(path);
        final var height = grid.length;
        final var width = grid[0].length;

        // Initialise costs to visit
        var costToReach = new long[height][width];
        fill2d(costToReach, UNSEEN_VAL);

        // A 3d array to store if we have a min cost parent reaching this node.
        // Note that the direction is *from* the (curr) to the (new), so backtracking will require
        // *going in the opposite direction*.
        var minCostParents = new boolean[height][width][Direction.length];

        // A* search
        final var cmp = Comparator.comparingLong(State::priorityCost);
        final var frontier = new PriorityQueue<>(cmp);
        frontier.offer(new State(height - 2, 1, Direction.EAST, 0));
        costToReach[height - 2][1] = 0;
        var minTargetCost = UNSEEN_VAL;

        while (!frontier.isEmpty()) {
            var curr = frontier.poll();
            if (curr.i == 1 && curr.j == width - 2) {
                if (minTargetCost < costToReach[curr.i][curr.j]) {
                    // We must have found all minimum cost paths. Backtrack all parents of (curr.i, curr.j).
                    break;
                } else {
                    // If first time (minTargetCost > 2^61), then set real min.
                    // If equal, then no change.
                    minTargetCost = costToReach[curr.i][curr.j];
                    System.out.printf("found min target = %d\n", minTargetCost);
                }
            }

            for (var newDir : curr.direction.nextDirs()) {
                var newI = curr.i + newDir.di;
                var newJ = curr.j + newDir.dj;
                // Lol, just had to check for E
                if (grid[newI][newJ] != '#') {
                    final var newCost = costToReach[curr.i][curr.j] + 1 + (newDir == curr.direction ? 0 : 1000);
                    // optimization: skip when we know we will exceed the min target cost
                    if (newCost > minTargetCost) continue;

                    var existingNewCost = costToReach[newI][newJ];
                    if (newCost == existingNewCost) {
                        System.out.println("got equal cost");
                        minCostParents[newI][newJ][newDir.ordinal()] = true;

                    } else if (newCost < existingNewCost) {
                        costToReach[newI][newJ] = newCost;
                        // For min cost backtracking
//                        Arrays.fill(minCostParents[newI][newJ], false);
                        minCostParents[newI][newJ][newDir.ordinal()] = true;
                        final var newPriority = newCost + heuristic(newI, newJ, height, width);
                        final var newState = new State(newI, newJ, newDir, newPriority);
                        frontier.offer(newState);
                    }
                }
            }
        }
        return getAllParents(minCostParents, 1, width - 2).size();
    }

    private static Set<Point> getAllParents(boolean[][][] minCostParents, final int i, final int j) {
        var seen = new LinkedHashSet<Point>();
        var queue = new ArrayDeque<Point>();
        var initial = new Point(i, j);
        queue.offer(initial);
        seen.add(initial);
        while (!queue.isEmpty()) {
            var curr = queue.poll();
            for (int d = 0; d < Direction.length; d++) {
                if (minCostParents[curr.i][curr.j][d]) {
                    var currDir = Direction.values[d];
                    var newI = curr.i - currDir.di;
                    var newJ = curr.j - currDir.dj;
                    var p = new Point(newI, newJ);
                    queue.offer(p);
                    seen.add(p);
                }
            }
        }
        seen.forEach(System.out::println);
        return seen;

    }


    record State(int i, int j, Direction direction, long priorityCost) {
    }

    record Point(int i, int j) {
    }
}

/*
Part 2 ideas: (2d or 3d array?)
1. instead of returning on the first time we reach the end, continue checking till we get a result more than the min.
2. Either costToReach or cameFrom has multiple parents which we can backtrack.
3. When newCost == existing costToReach value, still add it to the priority queue.
4. We don't need to explore visited edges again, but we should add the current path as a parent (idea of 'open' edges).
 */
