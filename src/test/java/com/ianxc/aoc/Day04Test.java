package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {
    @Test
    void testPart1Demo() {
        assertEquals(18, Day04.part1("day04/demo.txt"));
    }

    @Test
    void testPart1() {
        assertEquals(2358, Day04.part1("day04/input.txt"));
    }

    @Test
    void testPart2Demo() {
        assertEquals(9, Day04.part2("day04/demo.txt"));
    }

    @Test
    void testPart2() {
        assertEquals(1737, Day04.part2("day04/input.txt"));
    }
}
