package com.ianxc.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day14 {
    static final Pattern rowPattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");

    // height = tall = y
    // width = wide = x
    static long part1(String path, int height, int width, int iterations) {
        var robots = parseRobots(path).toList();
        for (int i = 0; i < iterations; i++) {
            for (var r : robots) {
                runStep(r, height, width);
            }
        }

        var centerHeight = height / 2;
        var centerWidth = width / 2;
        final var quadrants = new long[4];
        for (var r : robots) {
            if (r.x < centerWidth && r.y < centerHeight) quadrants[0]++;
            else if (r.x < centerWidth && r.y > centerHeight) quadrants[1]++;
            else if (r.x > centerWidth && r.y < centerHeight) quadrants[2]++;
            else if (r.x > centerWidth && r.y > centerHeight) quadrants[3]++;
        }

        System.out.println(Arrays.toString(quadrants));
        return quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
    }

    private static Stream<Robot> parseRobots(String path) {
        return Util.loadFile(path).stream()
                .map(Day14::parseRow);
    }

    static Robot parseRow(String s) {
        var m = rowPattern.matcher(s);
        //noinspection ResultOfMethodCallIgnored
        m.find();
        return new Robot(
                Integer.parseInt(m.group(1)),
                Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3)),
                Integer.parseInt(m.group(4)));
    }

    static void runStep(Robot r, int height, int width) {
        // Do not use %!!
        // https://stackoverflow.com/questions/5385024/mod-in-java-produces-negative-numbers
        r.x = Math.floorMod(r.x + r.dx, width);
        r.y = Math.floorMod(r.y + r.dy, height);
        // System.out.println(r);
    }

    // height = tall = y
    // width = wide = x
    static long part2(String path, int height, int width, int maxIterations) {
        var robots = parseRobots(path).toList();
        for (int i = 1; i <= maxIterations; i++) {
            for (var r : robots) {
                runStep(r, height, width);
            }
            if (checkIsXmas(robots, height, width)) {
                return i;
            }
        }

        return -1;
    }


    private static boolean checkIsXmas(List<Robot> robots, int height, int width) {
        var grid = new boolean[height][width];
        for (var r : robots) {
            grid[r.y][r.x] = true;
        }
        for (var row : grid) {
            var longestRun = 0;
            for (var cell : row) {
                if (longestRun > 30) return true;
                if (cell) longestRun++;
                else longestRun = 0;
            }
        }
        return false;
    }

    static final class Robot {
        final int dx;
        final int dy;
        int x;
        int y;

        Robot(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public String toString() {
            return "RowSpec[" +
                    "x=" + x + ", " +
                    "y=" + y + ", " +
                    "dx=" + dx + ", " +
                    "dy=" + dy + ']';
        }
    }
}
