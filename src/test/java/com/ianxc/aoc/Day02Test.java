package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day02Test {
    public static Stream<int[]> okPart2Cases() {
        return Stream.of(
                new int[]{0, 4, 5, 6},
                new int[]{4, 5, 6, 10},
                new int[]{4, 6, 3, 2},
                new int[]{4, 3, 8, 2, 1},
                new int[]{4, 3, 3, 2, 1}
        );
    }

    @Test
    void testPart1Demo() {
        var res = Day02.part1("day02/demo.txt");

        assertEquals(2, res);
    }

    @Test
    void testPart1() {
        var res = Day02.part1("day02/input.txt");

        assertEquals(383, res);
    }

    @Test
    void testPart2Demo() {
        var res = Day02.part2("day02/demo.txt");

        assertEquals(4, res);
    }

    @Test
    void testPart2() {
        var res = Day02.part2("day02/input.txt");

        assertEquals(436, res);
    }

    @ParameterizedTest
    @MethodSource("okPart2Cases")
    void testComputeContribution2(int[] ints) {
        assertEquals(1, Day02.computeContribution2(ints));
    }
}
