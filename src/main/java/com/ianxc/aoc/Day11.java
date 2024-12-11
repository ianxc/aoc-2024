package com.ianxc.aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

@SuppressWarnings("DuplicatedCode")
public class Day11 {
    static long part1(String path, int iterations) {
        var result = parseInput(path).parallel();
        for (int i = 0; i < iterations; i++) {
            result = result.flatMap(Day11::applySingle);
            System.out.println("executed iteration i=" + i);
        }
        return result.count();
    }

    static LongStream parseInput(String path) {
        return Arrays.stream(Util.loadFile(path)
                        .get(0)
                        .split(" "))
                .mapToLong(Long::parseLong);
    }

    static LongStream applySingle(long n) {
        if (n == 0L) return LongStream.of(1);
        var numDigits = (long) Math.floor(Math.log10(n)) + 1;
        if ((numDigits & 1) == 0) {
            long pow = numDigits / 2;
            var divisor = (long) Math.pow(10, pow);
            return LongStream.of(n / divisor, n % divisor);
        }
        return LongStream.of(2024L * n);
    }

    static long part2(String path, int iterations) {
        var input = parseInput(path);
        var seen = new HashMap<Stage, Long>();
        return input.map(n -> dp(n, iterations, seen)).sum();
    }

    static long dp(long n, int level, Map<Stage, Long> seen) {
        if (level == 0) return 1;
        var stage = new Stage(n, level);
        var maybeSeen = seen.get(stage);
        if (maybeSeen != null) return maybeSeen;

        if (n == 0) {
            var result = dp(1, level - 1, seen);
            seen.put(stage, result);
            return result;
        }
        var numDigits = (long) Math.floor(Math.log10(n)) + 1;
        if ((numDigits & 1) == 0) {
            long pow = numDigits / 2;
            var divisor = (long) Math.pow(10, pow);
            var result = dp(n / divisor, level - 1, seen) + dp(n % divisor, level - 1, seen);
            seen.put(stage, result);
            return result;
        }
        var result = dp(2024 * n, level - 1, seen);
        seen.put(stage, result);
        return result;
    }

    record Stage(long n, int level) {
    }
}
