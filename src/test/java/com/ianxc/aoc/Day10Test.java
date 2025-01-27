package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day10Test {
    @ParameterizedTest
    @CsvSource({
            "day10/demo.txt,  2",
            "day10/demo2.txt, 4",
            "day10/demo3.txt, 3",
            "day10/demo4.txt, 36"
    })
    void testPart1Demo(String path, int expectedScore) {
        var res = Day10.part1(path);

        assertEquals(expectedScore, res);
    }

    @Test
    void testPart1() {
        var res = Day10.part1("day10/input.txt");

        assertEquals(744, res);
    }

    @ParameterizedTest
    @CsvSource({
            "day10/demo5.txt,   3",
            "day10/demo2.txt,  13",
            "day10/demo6.txt, 227",
            "day10/demo4.txt,  81"
    })
    void testPart2Demo(String path, int expectedSumRatings) {
        var res = Day10.part2(path);

        assertEquals(expectedSumRatings, res);
    }

    @Test
    void testPart2() {
        var res = Day10.part2("day10/input.txt");

        assertEquals(1651, res);
    }
}
