package com.ianxc.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    @ParameterizedTest
    @CsvSource({
            "day13/demo.txt, 480",
            "day13/input.txt, 40369"
    })
    void testPart1(String path, long expectedMinTokens) {
        var res = Day13.part1(path);

        assertEquals(expectedMinTokens, res);
    }

    @ParameterizedTest
    @CsvSource({
            "day13/demo.txt, 875318608908",
            "day13/input.txt, 72587986598368"
    })
    void testPart2(String path, long expectedMinTokens) {
        var res = Day13.part2(path);

        assertEquals(expectedMinTokens, res);
    }

}
