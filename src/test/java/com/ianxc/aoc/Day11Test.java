package com.ianxc.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
    @ParameterizedTest
    @CsvSource({
            "day11/demo.txt, 1, 7",
            "day11/demo2.txt, 6, 22",
            "day11/demo2.txt, 25, 55312",
            "day11/input.txt, 25, 189092",
            "day11/input.txt, 75, 224869647102559"
    })
    void testPart1(String path, int iterations, long expectedCount) {
        var res = Day11.part1(path, iterations);

        assertEquals(expectedCount, res);
    }

    @ParameterizedTest
    @CsvSource({
            "day11/demo.txt, 1, 7",
            "day11/demo2.txt, 6, 22",
            "day11/demo2.txt, 25, 55312",
            "day11/input.txt, 25, 189092",
            "day11/input.txt, 75, 224869647102559",
    })
    void testPart2(String path, int iterations, long expectedCount) {
        var res = Day11.part2(path, iterations);

        assertEquals(expectedCount, res);
    }
}
