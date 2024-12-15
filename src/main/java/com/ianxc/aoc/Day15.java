package com.ianxc.aoc;

import java.util.List;

public class Day15 {
    static long part1(String path) {
        var input = Util.loadFile(path);
        var bi = findBlankIndex(input);
        var grid = parseGrid(input, bi);
        var steps = parseSteps(input, bi);
        var robot = findRobot(grid);

        for (final var step: steps) {
            switch (step) {
                case '^' -> {
                    System.out.println("up");
                }
                case '>' -> {
                    System.out.println("right");
                }
                case 'v' -> {
                    System.out.println("down");
                }
                case '<' -> {
                    System.out.println("left");
                }
                default -> throw new IllegalStateException("Unexpected value: " + step);
            }
        }

        return 0;
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

    static class Point {
        int i;
        int j;

        Point(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
