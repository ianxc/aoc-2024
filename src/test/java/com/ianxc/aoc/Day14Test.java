package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    @Test
    void testPart1Demo() {
        var res = Day14.part1("day14/demo.txt", 7, 11, 100);

        assertEquals(12L, res);
    }

    @Test
    void testPart1() {
        var res = Day14.part1("day14/input.txt", 103, 101, 100);

        assertEquals(224438715L, res);
    }

    @Test
    void testPart1Demo2() {
        var res = Day14.part1("day14/demo2.txt", 7, 11, 5);

        assertEquals(0L, res);
    }

    @Test
    void testPart2() {
        var res = Day14.part2("day14/input.txt", 103, 101, 100_000);

        assertEquals(7603L, res);
    }

}
