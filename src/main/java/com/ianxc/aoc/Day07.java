package com.ianxc.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day07 {
    static long part1(String path) {
        return parseSpecs(Util.loadFile(path))
                .filter(spec -> dfs(spec, 1, spec.components[0]))
                .mapToLong(spec -> spec.desired)
                .sum();
    }

    static Stream<Spec> parseSpecs(List<String> input) {
        return input.parallelStream()
                .map(s -> s.split(" "))
                .map(xs -> {
                    var desiredStr = xs[0].substring(0, xs[0].length() - 1);
                    var components = Arrays.stream(xs).skip(1).mapToLong(Long::parseLong).toArray();
                    return new Spec(Long.parseLong(desiredStr), components);
                });
    }

    static boolean dfs(Spec spec, int currIndex, long currValue) {
        if (currIndex == spec.components.length) {
            return spec.desired == currValue;
        }
        if (spec.desired < currValue) {
            return false;
        }

        var currComponent = spec.components[currIndex];
        return dfs(spec, currIndex + 1, currValue * currComponent) ||
                dfs(spec, currIndex + 1, currValue + currComponent);
    }

    static boolean dfs2(Spec spec, int currIndex, long currValue) {
        if (currIndex == spec.components.length) {
            return spec.desired == currValue;
        }
        if (spec.desired < currValue) {
            return false;
        }

        var currComponent = spec.components[currIndex];
        return dfs2(spec, currIndex + 1, currValue * currComponent) ||
                dfs2(spec, currIndex + 1, currValue + currComponent) ||
                dfs2(spec, currIndex + 1, currValue * nextPow10(currComponent) + currComponent);
    }

    static long nextPow10(long n) {
        return (long) Math.pow(10, 1 + Math.floor(Math.log10(n)));
    }

    static long part2(String path) {
        return parseSpecs(Util.loadFile(path))
                .filter(spec -> dfs2(spec, 1, spec.components[0]))
                .mapToLong(spec -> spec.desired)
                .sum();
    }

    record Spec(long desired, long[] components) {
    }
}
