package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class Day13 {
    private static final long UNREACHABLE = Long.MAX_VALUE / 2;
    private static final Pattern buttonPattern = Pattern.compile("Button ([A-Z]): X\\+(\\d+), Y\\+(\\d+)");
    private static final Pattern prizePattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    private static final int N = 101;

    static long part1(String path) {
        var input = Util.loadFile(path);
        var scenarios = getScenarios(input, 0);
        System.out.println(scenarios);
        return scenarios.stream()
                .mapToLong(s -> minCostToReach(s.prize.x, s.prize.y, s.a.x, s.a.y, s.a.c, s.b.x, s.b.y, s.b.c))
                .filter(moves -> moves != UNREACHABLE)
                .sum();
    }

    private static ArrayList<Scenario> getScenarios(List<String> input, long delta) {
        var scenarios = new ArrayList<Scenario>();
        System.out.println(input);
        for (int i = 0; i < input.size(); i += 4) {
            var a = parseButton(input.get(i));
            var b = parseButton(input.get(i + 1));
            var p = parsePrize(input.get(i + 2), delta);
            scenarios.add(new Scenario(p, a, b));
        }
        return scenarios;
    }

    static Button parseButton(String line) {
        System.out.println("parseButton: " + line);
        var m = buttonPattern.matcher(line);
        if (!m.find()) throw new IllegalStateException("invalid regex");
        var label = m.group(1).charAt(0);
        var cost = label == 'A' ? 3 : 1;
        return new Button(label, Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), cost);
    }

    static Point parsePrize(String line, long delta) {
        System.out.println("parsePrize: " + line);
        var m = prizePattern.matcher(line);
        if (!m.find()) throw new IllegalStateException("invalid regex");
        return new Point(delta + Long.parseLong(m.group(1)), delta + Long.parseLong(m.group(2)));
    }

    static long minCostToReach(long pa, long pb, int x1, int y1, int c1, int x2, int y2, int c2) {
        var minCost = UNREACHABLE;
        for (int a = 0; a <= N; a++) {
            for (int b = 0; b <= N; b++) {
                var currentX = a * x1 + b * x2;
                var currentY = a * y1 + b * y2;
                if (currentX > pa || currentY > pb) break;
                if (currentX == pa && currentY == pb) {
                    final var cost = a * c1 + b * c2;
                    minCost = Math.min(minCost, cost);
                }
            }
        }
        // System.out.println(minCost);
        return minCost;
    }

    static long det(long a, long b, long c, long d) {
        return a * d - b * c;
    }

    static Optional<Long> solveEquationsAndComputeCost(Scenario s) {
        // Use Cramer's rule
        var d = det(s.a.x, s.b.x, s.a.y, s.b.y);
        var da = det(s.prize.x, s.b.x, s.prize.y, s.b.y);
        var db  = det(s.a.x, s.prize.x, s.a.y, s.prize.y);
        var aPresses = da / d;
        var bPresses = db / d;
        if (aPresses * s.a.x + bPresses * s.b.x != s.prize.x || aPresses * s.a.y + bPresses * s.b.y != s.prize.y) {
            return Optional.empty();
        }
        return Optional.of(3 * aPresses + bPresses);
    }

    static long part2(String path) {
        var input = Util.loadFile(path);
        var scenarios = getScenarios(input, 10000000000000L);
        return scenarios.stream()
                .flatMapToLong(s -> solveEquationsAndComputeCost(s).stream().mapToLong(Long::longValue))
                .sum();
    }

    record Point(long x, long y) {
    }

    record Scenario(Point prize, Button a, Button b) {
    }

    record Button(char label, int x, int y, int c) {
    }
}
