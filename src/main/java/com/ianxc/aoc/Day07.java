package com.ianxc.aoc;

import java.util.Arrays;

public class Day07 {
    static long part1(String path) {
        return Util.loadFile(path)
                .stream()
                .map(s -> s.split(" "))
                .map(xs -> {
                    var desiredStr = xs[0].substring(0, xs[0].length() - 1);
                    var components = Arrays.stream(xs).skip(1).mapToLong(Long::parseLong).toArray();
                    return new Spec(Long.parseLong(desiredStr), components);
                })
                .peek(spec -> System.out.printf("spec: desired=%d components=%s\n",
                        spec.desired, Arrays.toString(spec.components)))
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

    static long part2(String path) {
        return 0;
    }

    record Spec(long desired, long[] components) {
    }
}
