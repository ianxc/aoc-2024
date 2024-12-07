package com.ianxc.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 5, unit = TimeUnit.SECONDS)
class Day07Test {
    @Test
    void testPart1() {
        var res = Day07.part1("day07/input.txt");

        assertEquals(4433, res);
    }

    @Test
    void testPart1demo() {
        var res = Day07.part1("day07/demo.txt");

        assertEquals(41, res);
    }

    @Test
    void testPart2() {
        var res = Day07.part2("day07/input.txt");

        assertEquals(1516, res);
    }

    @Test
    void testPart2demo() {
        var res = Day07.part2("day07/demo.txt");

        assertEquals(6, res);
    }
}
