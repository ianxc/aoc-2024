package com.ianxc.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    @Test
    void testPart1() {
        var res = Day14.part1("day14/demo.txt", 7, 11, 100);

        assertEquals(12L, res);
    }

}
