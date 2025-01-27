package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
@Timeout(value = 5, unit = TimeUnit.SECONDS)
class Day07Test {
    @Test
    void testPart1() {
        var res = Day07.part1("day07/input.txt");

        assertEquals(20665830408335L, res);
    }

    @Test
    void testPart1demo() {
        var res = Day07.part1("day07/demo.txt");

        assertEquals(3749L, res);
    }

    @Test
    void testPart2() {
        var res = Day07.part2("day07/input.txt");

        assertEquals(354060705047464L, res);
    }

    @Test
    void testPart2demo() {
        var res = Day07.part2("day07/demo.txt");

        assertEquals(11387L, res);
    }
}
