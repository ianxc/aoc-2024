package com.ianxc.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {
    @ParameterizedTest
    @CsvSource({
            "day12/demo.txt, 140",
            "day12/demo2.txt, 772",
            "day12/demo3.txt, 1930",
            "day12/input.txt, 1375476"
    })
    void testPart1(String path, long expectedSol) {
        var res = Day12.part1(path);

        assertEquals(expectedSol, res);
    }
}
