package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day03Test {
    @Test
    void testPart1Demo() {
        var res = Day03.part1("day03/demo.txt");

        assertEquals(161, res);
    }

    @Test
    void testPart1() {
        var res = Day03.part1("day03/input.txt");

        assertEquals(178794710, res);
    }

    @Test
    void testPart2Demo() {
        var res = Day03.part2("day03/demo2.txt");

        assertEquals(48, res);
    }

    @Test
    void testPart2() {
        var res = Day03.part2("day03/input.txt");

        assertEquals(76729637, res);
    }
}
