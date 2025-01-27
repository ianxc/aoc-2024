package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day09Test {
    @Test
    void testPart1Demo() {
        var res = Day09.part1("day09/demo.txt");

        assertEquals(1928L, res);
    }

    @Test
    void testPart1Demo2() {
        var res = Day09.part1("day09/demo2.txt");

        assertEquals(60L, res);
    }

    @Test
    void testPart1() {
        var res = Day09.part1("day09/input.txt");

        assertEquals(6331212425418L, res);
    }

    @Test
    void testPart2Demo() {
        var res = Day09.part2("day09/demo.txt");

        assertEquals(2858L, res);
    }

    @Test
    void testPart2() {
        var res = Day09.part2("day09/input.txt");

        assertEquals(6363268339304L, res);
    }
}
