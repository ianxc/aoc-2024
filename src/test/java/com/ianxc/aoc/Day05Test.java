package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day05Test {

    @Test
    void testPart1() {
        var res = Day05.part1("day05/input.txt");

        assertEquals(5588, res);
    }

    @Test
    void testPart2() {
        var res = Day05.part2("day05/input.txt");
        // too high: 5650
        // too low: 5162
        assertEquals(5331, res);
    }

}
