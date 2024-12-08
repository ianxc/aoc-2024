package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
