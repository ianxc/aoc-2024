package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {
    @Test
    void testPart1Demo() {
        var res = Day15.part1("day15/demo.txt");

        assertEquals(10092, res);
    }

    @Test
    void testPart1Demo2() {
        var res = Day15.part1("day15/demo2.txt");

        assertEquals(2028, res);
    }

    @Test
    void testPart1() {
        var res = Day15.part1("day15/input.txt");

        assertEquals(1485257, res);
    }
}
