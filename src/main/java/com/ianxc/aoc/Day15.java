package com.ianxc.aoc;

import java.util.List;

public class Day15 {
    static long part1(String path) {
        var input = Util.loadFile(path);
        var bi = findBlankIndex(input);
        var grid = parseGrid(input, bi);
        final var height = grid.length;
        final var width = grid[0].length;
        var steps = parseSteps(input, bi);
        var robot = findRobot(grid);

        for (final var step : steps) {
            switch (step) {
                case '^' -> {
                    for (int i = robot.i - 1; i >= 0; i--) {
                        if (grid[i][robot.j] == '#') break;
                        if (grid[i][robot.j] == '.') {
                            for (int i2 = i; i2 < robot.i; i2++) {
                                grid[i2][robot.j] = grid[i2 + 1][robot.j];
                            }
                            grid[robot.i][robot.j] = '.';
                            robot.i--;
                            break;
                        }
                    }
                }
                case '>' -> {
                    for (int j = robot.j + 1; j < width; j++) {
                        if (grid[robot.i][j] == '#') break;
                        if (grid[robot.i][j] == '.') {
                            for (int j2 = j; j2 > robot.j; j2--) {
                                grid[robot.i][j2] = grid[robot.i][j2 - 1];
                            }
                            grid[robot.i][robot.j] = '.';
                            robot.j++;
                            break;
                        }
                    }
                }
                case 'v' -> {
                    for (int i = robot.i + 1; i < height; i++) {
                        if (grid[i][robot.j] == '#') break;
                        if (grid[i][robot.j] == '.') {
                            for (int i2 = i; i2 > robot.i; i2--) {
                                grid[i2][robot.j] = grid[i2 - 1][robot.j];
                            }
                            grid[robot.i][robot.j] = '.';
                            robot.i++;
                            break;
                        }
                    }
                }
                case '<' -> {
                    for (int j = robot.j - 1; j >= 0; j--) {
                        if (grid[robot.i][j] == '#') break;
                        if (grid[robot.i][j] == '.') {
                            for (int j2 = j; j2 < robot.j; j2++) {
                                grid[robot.i][j2] = grid[robot.i][j2 + 1];
                            }
                            grid[robot.i][robot.j] = '.';
                            robot.j--;
                            break;
                        }
                    }
                }
                default -> throw new IllegalStateException("Unexpected step: " + step);
            }
        }

        return computeGpsSum(grid);
    }

    private static char[][] parseGrid(List<String> input, int bi) {
        return input.stream().limit(bi).map(String::toCharArray).toArray(char[][]::new);
    }

    static int findBlankIndex(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).isEmpty()) {
                return i;
            }
        }
        throw new IllegalArgumentException("input should contain empty line");
    }

    static List<Character> parseSteps(List<String> input, int blankIndex) {
        return input.stream()
                .skip(blankIndex + 1)
                .flatMapToInt(String::chars)
                .mapToObj(i -> (char) i)
                .toList();
    }

    static Point findRobot(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '@') return new Point(i, j);
            }
        }
        throw new IllegalArgumentException("grid should contain robot");
    }

    static long computeGpsSum(char[][] grid) {
        var gpsSum = 0L;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'O') {
                    gpsSum += 100L * i + j;
                }
            }
        }
        return gpsSum;
    }

    static class Point {
        int i;
        int j;

        Point(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
