package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.Arrays;

public class Day02 {
    static int part1(String path) {
        var sum = 0;
        for (var line : Util.loadFile(path)) {
            var ints = splitToInts(line);
            sum += computeContribution1(ints);
        }
        return sum;
    }

    static int[] splitToInts(String line) {
        String[] strs = line.split(" ");
        return Arrays.stream(strs)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    static int computeContribution1(int[] ints) {
        var m = ints[0] < ints[1] ? 1 : -1;
        var ok = true;
        for (int i = 1; i < ints.length; i++) {
            var diff = ints[i] - ints[i - 1];
            diff *= m;
            if (diff < 1 || diff > 3) {
                ok = false;
                break;
            }
        }
        return ok ? 1 : 0;
    }


    static int part2(String path) {
        var sum = 0;
        for (var line : Util.loadFile(path)) {
            var ints = splitToInts(line);
            sum += computeContribution2(ints);
        }
        return sum;
    }

    static int computeContribution2(int[] ints) {
        if (computeContribution1(ints) == 1) {
            return 1;
        }
        var intsList = Arrays.stream(ints).boxed().toList();
        for (int i = 0; i < ints.length; i++) {
            var newList = new ArrayList<>(intsList);
            newList.remove(i);
            System.out.print(newList + " ");
            if (computeContribution1(newList.stream().mapToInt(Integer::intValue).toArray()) == 1) {
                return 1;
            }
        }

        System.out.println("\n\n");
        return 0;
    }
}
