package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day08Test {
    @Test
    void testPart1Demo() {
        var res = Day08.part1("day08/demo.txt");

        assertEquals(14, res);
    }

    @Test
    void testPart1() {
        var res = Day08.part1("day08/input.txt");

        assertEquals(396, res);
    }

    @Test
    void testPart2Demo() {
        var res = Day08.part2("day08/demo.txt");

        assertEquals(34, res);
    }

    @Test
    void testPart2() {
        var res = Day08.part2("day08/input.txt");

        assertEquals(1200, res);
    }
}
