package com.ianxc.aoc;

import java.util.Arrays;

@SuppressWarnings("DuplicatedCode")
public class Day07 {
    static long part1(String path) {
        return Util.loadFile(path)
                .parallelStream()
                .map(s -> s.split(" "))
                .map(xs -> {
                    var desiredStr = xs[0].substring(0, xs[0].length() - 1);
                    var components = Arrays.stream(xs).skip(1).mapToLong(Long::parseLong).toArray();
                    return new Spec(Long.parseLong(desiredStr), components);
                })
//                .peek(spec -> System.out.printf("spec: desired=%d components=%s\n",
//                        spec.desired, Arrays.toString(spec.components)))
                .filter(spec -> dfs(spec, 1, spec.components[0]))
                .mapToLong(spec -> spec.desired)
                .sum();
    }

    static boolean dfs(Spec spec, int currIndex, long currValue) {
//        System.out.printf("spec.desired=%d, currIndex=%d, currValue=%d\n", spec.desired, currIndex, currValue);
        if (currIndex == spec.components.length) {
            return spec.desired == currValue;
        }
        if (spec.desired < currValue) {
            return false;
        }

        return dfs(spec, currIndex + 1, currValue * spec.components[currIndex]) ||
                dfs(spec, currIndex + 1, currValue + spec.components[currIndex]);
    }

    static boolean dfs2(Spec spec, int currIndex, long currValue) {
        if (currIndex == spec.components.length) {
            return spec.desired == currValue;
        }
        if (spec.desired < currValue) {
            return false;
        }

        return dfs2(spec, currIndex + 1, currValue * spec.components[currIndex]) ||
                dfs2(spec, currIndex + 1, currValue + spec.components[currIndex]) ||
                dfs2(spec, currIndex + 1, currValue * nextPow10(spec.components[currIndex]) + spec.components[currIndex]);
    }

    static long nextPow10(long n) {
        return (long) Math.pow(10, 1 + Math.floor(Math.log10(n)));
    }

    static long part2(String path) {
        return Util.loadFile(path)
                .parallelStream()
                .map(s -> s.split(" "))
                .map(xs -> {
                    var desiredStr = xs[0].substring(0, xs[0].length() - 1);
                    var components = Arrays.stream(xs).skip(1).mapToLong(Long::parseLong).toArray();
                    return new Spec(Long.parseLong(desiredStr), components);
                })
//                .peek(spec -> System.out.printf("spec: desired=%d components=%s\n",
//                        spec.desired, Arrays.toString(spec.components)))
                .filter(spec -> dfs2(spec, 1, spec.components[0]))
                .mapToLong(spec -> spec.desired)
                .sum();
    }

    record Spec(long desired, long[] components) {
    }
}
