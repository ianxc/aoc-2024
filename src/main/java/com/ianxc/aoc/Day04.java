package com.ianxc.aoc;

import java.util.ArrayList;

public class Day04 {
    static int part1(String path) {
        var input = new ArrayList<char[]>();
        for (var line : Util.loadFile(path)) {
            input.add(line.toCharArray());
        }

        var x = input.toArray(char[][]::new);
        //        System.out.println(Arrays.deepToString(x));
        final var height = x.length;
        final var width = x[0].length;
        final var initial = 3;
        var count = 0;
        var curr = 0;
        for (int i = 0; i < height; i++) {
            for (int j = initial; j < width; j++) {
                if ((x[i][j - 3] == 'X' && x[i][j - 2] == 'M' && x[i][j - 1] == 'A' && x[i][j] == 'S') ||
                        (x[i][j - 3] == 'S' && x[i][j - 2] == 'A' && x[i][j - 1] == 'M' && x[i][j] == 'X')) {
                    curr++;
                }
            }
        }
        System.out.printf("horizontal=%d\n", curr);
        count += curr;
        curr = 0;

        for (int i = initial; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((x[i - 3][j] == 'X' && x[i - 2][j] == 'M' && x[i - 1][j] == 'A' && x[i][j] == 'S') ||
                        (x[i - 3][j] == 'S' && x[i - 2][j] == 'A' && x[i - 1][j] == 'M' && x[i][j] == 'X')) {
                    curr++;
                }
            }
        }
        System.out.printf("vertical=%d\n", curr);
        count += curr;
        curr = 0;

        for (int i = initial; i < height; i++) {
            for (int j = initial; j < width; j++) {
                if ((x[i - 3][j - 3] == 'X' && x[i - 2][j - 2] == 'M' && x[i - 1][j - 1] == 'A' && x[i][j] == 'S') ||
                        (x[i - 3][j - 3] == 'S' && x[i - 2][j - 2] == 'A' && x[i - 1][j - 1] == 'M' && x[i][j] == 'X')) {
                    curr++;
                }
            }
        }

        System.out.printf("backslash=%d\n", curr);
        count += curr;
        curr = 0;

        for (int i = initial; i < height; i++) {
            for (int j = width - initial - 1; j >= 0; j--) {
                if ((x[i - 3][j + 3] == 'X' && x[i - 2][j + 2] == 'M' && x[i - 1][j + 1] == 'A' && x[i][j] == 'S') ||
                        (x[i - 3][j + 3] == 'S' && x[i - 2][j + 2] == 'A' && x[i - 1][j + 1] == 'M' && x[i][j] == 'X')) {
                    curr++;
                }
            }
        }

        System.out.printf("slash=%d\n", curr);
        count += curr;

        return count;
    }

    static int part2(String path) {
        var input = new ArrayList<char[]>();
        for (var line : Util.loadFile(path)) {
            input.add(line.toCharArray());
        }

        var x = input.toArray(char[][]::new);

        final var height = x.length;
        final var width = x[0].length;
        final var initial = 2;
        var count = 0;

        for (int i = initial; i < height; i++) {
            for (int j = initial; j < width; j++) {
                if (x[i - 1][j - 1] != 'A') {
                    continue;
                }

                if ((x[i - 2][j - 2] == 'M' && x[i - 2][j] == 'M' && x[i][j] == 'S' && x[i][j - 2] == 'S') ||
                        (x[i - 2][j - 2] == 'S' && x[i - 2][j] == 'S' && x[i][j] == 'M' && x[i][j - 2] == 'M') ||
                        (x[i - 2][j - 2] == 'M' && x[i - 2][j] == 'S' && x[i][j] == 'S' && x[i][j - 2] == 'M') ||
                        (x[i - 2][j - 2] == 'S' && x[i - 2][j] == 'M' && x[i][j] == 'M' && x[i][j - 2] == 'S')
                ) {
                    count++;
                }
            }
        }
        return count;
    }
}
