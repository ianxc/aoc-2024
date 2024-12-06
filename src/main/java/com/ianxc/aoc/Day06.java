package com.ianxc.aoc;

public class Day06 {
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
                nextX = x + offset[0];
                nextY = y + offset[1];
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
            case UP -> new int[]{-1, 0};
            case RIGHT -> new int[]{0, 1};
            case DOWN -> new int[]{1, 0};
            case LEFT -> new int[]{0, -1};
        };
    }

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    record CoordState(int x, int y) {
    }
}