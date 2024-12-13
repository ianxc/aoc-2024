package com.ianxc.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    @ParameterizedTest
    @CsvSource({
            "day13/demo.txt, 480",
            "day13/input.txt, 0"
    })
    void testPart1(String path, long expectedMinTokens) {
        var res = Day13.part1(path);

        assertEquals(expectedMinTokens, res);
    }

}
