package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day16Test {
    @ParameterizedTest
    @CsvSource({
            "day16/demo.txt, 7036",
            "day16/demo2.txt, 11048"
    })
    void testPart1Demo(String path, long expected) {
        var res = Day16.part1(path);

        assertEquals(expected, res);
    }

    @Test
    void testPart1() {
        var res = Day16.part1("day16/input.txt");

        assertEquals(130536, res);
    }

    @ParameterizedTest
    @CsvSource({
            "day16/demo.txt, 45",
            "day16/demo2.txt, 64"
    })
    void testPart2Demo(String path, long expected) {
        var res = Day16.part2(path);

        assertEquals(expected, res);
    }

    @Test
    void testPart2() {
        var res = Day16.part2("day16/input.txt");

        assertEquals(1069, res);
    }

}
