package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {
    @Test
    void testDay01() {
        var result = Day01.part1("day01/input.txt");

        assertEquals(1530215, result);
    }

    @Test
    void testDay02() {
        var result = Day01.part2("day01/input.txt");

        assertEquals(26800609, result);
    }
}
