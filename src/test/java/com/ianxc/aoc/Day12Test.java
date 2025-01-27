package com.ianxc.aoc;

import com.ianxc.aoc.util.WithSilencePrintOutput;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WithSilencePrintOutput
class Day12Test {
    @ParameterizedTest
    @CsvSource({
            "day12/demo.txt, 140",
            "day12/demo2.txt, 772",
            "day12/demo3.txt, 1930",
            "day12/input.txt, 1375476"
    })
    void testPart1(String path, long expectedSol) {
        var res = Day12.part1(path);

        assertEquals(expectedSol, res);
    }

    @ParameterizedTest
    @CsvSource({
            "day12/demo.txt, 80",
            "day12/demo2.txt, 436",
            "day12/demo4.txt, 236",
            "day12/demo5.txt, 368",
            "day12/demo3.txt, 1206",
            "day12/input.txt, 821372"
    })
    void testPart2(String path, long expectedSol) {
        var res = Day12.part2(path);

        assertEquals(expectedSol, res);
    }
}
