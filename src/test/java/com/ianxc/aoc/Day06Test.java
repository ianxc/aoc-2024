package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day06Test {
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testPart1() {
        var res = Day06.part1("day06/input.txt");

        assertEquals(4433, res);
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testPart1demo() {
        var res = Day06.part1("day06/demo.txt");

        assertEquals(41, res);
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testPart2() {
        var res = Day06.part2("day06/input.txt");

        assertEquals(1516, res);
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testPart2demo() {
        var res = Day06.part2("day06/demo.txt");

        assertEquals(6, res);
    }
}
