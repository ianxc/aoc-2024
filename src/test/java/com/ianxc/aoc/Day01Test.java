package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {
    @Test
    void testPart1Demo() {
        var result = Day01.part1("day01/demo.txt");

        assertEquals(11, result);
    }

    @Test
    void testPart1() {
        var result = Day01.part1("day01/input.txt");

        assertEquals(1530215, result);
    }

    @Test
    void testPart2Demo() {
        var result = Day01.part2("day01/demo.txt");

        assertEquals(31, result);
    }

    @Test
    void testPart2() {
        var result = Day01.part2("day01/input.txt");

        assertEquals(26800609, result);
    }
}
