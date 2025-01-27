package com.ianxc.aoc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

@SuppressWarnings("DuplicatedCode")
public class Day06 {
    private static final int[] OFFSET_UP = {-1, 0};
    private static final int[] OFFSET_RIGHT = {0, 1};
    private static final int[] OFFSET_DOWN = {1, 0};
    private static final int[] OFFSET_LEFT = {0, -1};

    static int part1(String path) {
        var input = Util.loadFile(path);
        var grid = input.stream().map(String::toCharArray).toArray(char[][]::new);
        var initial = findGuard(grid);
        var x = initial.x;
        var y = initial.y;

        final var xMax = grid.length;
        final var yMax = grid[0].length;
        var visited = 0;
        var currDir = Direction.UP;
        var offset = offsetOf(currDir);
        while (x >= 0 && x < xMax && y >= 0 && y < yMax) {
            if (grid[x][y] != 'X') {
                grid[x][y] = 'X';
                visited++;
            }
            var nextX = x + offset[0];
            var nextY = y + offset[1];
            if (!(nextX >= 0 && nextX < xMax)) break;
            if (!(nextY >= 0 && nextY < yMax)) break;

            if (grid[nextX][nextY] == '#') {
                currDir = nextDirection(currDir);
                offset = offsetOf(currDir);
                nextX = x;
                nextY = y;
            }
            x = nextX;
            y = nextY;
        }

        System.out.println(gridToStr(grid));
        return visited;
    }

    static String gridToStr(char[][] grid) {
        var sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                sb.append(grid[i][j]);
            }
            sb.append('\n');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    static CoordState findGuard(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '^') {
                    return new CoordState(i, j);
                }
            }
        }
        throw new IllegalArgumentException("invalid grid");
    }

    static Direction nextDirection(Direction dir) {
        return switch (dir) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    static int[] offsetOf(Direction dir) {
        return switch (dir) {
            case UP -> OFFSET_UP;
            case RIGHT -> OFFSET_RIGHT;
            case DOWN -> OFFSET_DOWN;
            case LEFT -> OFFSET_LEFT;
        };
    }

    static int part2(String path) {
        final var input = Util.loadFile(path);
        var g = input.stream().map(String::toCharArray).toArray(char[][]::new);
        final var xMax = g.length;
        final var yMax = g[0].length;
        final var guard = findGuard(g);
        return (int) IntStream.range(0, xMax)
                .parallel()
                .mapToLong(i ->
                        IntStream.range(0, yMax)
                                .parallel()
                                .filter(j -> g[i][j] == '.')
                                .filter(j -> tryPosition(copy2d(g), i, j, guard.x, guard.y))
                                .count()
                ).sum();
    }

    static char[][] copy2d(char[][] grid) {
        var output = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            output[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return output;
    }

    static boolean tryPosition(char[][] grid, int i, int j, int x, int y) {
        grid[i][j] = '#';
        final var xMax = grid.length;
        final var yMax = grid[0].length;
        var visited = new HashSet<VisitedCoord>();
        var currDir = Direction.UP;
        var offset = offsetOf(currDir);
        while (x >= 0 && x < xMax && y >= 0 && y < yMax) {
            grid[x][y] = 'X';
            var coord = new VisitedCoord(x, y, currDir);
            var isNew = visited.add(coord);
            if (!isNew) {
                return true;
            }
            var nextX = x + offset[0];
            var nextY = y + offset[1];
            if (!(nextX >= 0 && nextX < xMax)) break;
            if (!(nextY >= 0 && nextY < yMax)) break;

            if (grid[nextX][nextY] == '#') {
                currDir = nextDirection(currDir);
                offset = offsetOf(currDir);
                // Turn *without* advancing yet (need to record visited).
                nextX = x;
                nextY = y;
            }
            x = nextX;
            y = nextY;
        }
        return false;
    }


    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    record CoordState(int x, int y) {
    }

    record VisitedCoord(int x, int y, Direction d) {
    }
}
