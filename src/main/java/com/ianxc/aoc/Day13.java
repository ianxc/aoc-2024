package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day13 {
    private static final long UNREACHABLE = Long.MAX_VALUE / 2;
    private static final Pattern buttonPattern = Pattern.compile("Button ([A-Z]): X\\+(\\d+), Y\\+(\\d+)");
    private static final Pattern prizePattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    private static final int N = 101;

    static long part1(String path) {
        var input = Util.loadFile(path);
        var scenarios = getScenarios(input);
        System.out.println(scenarios);
        return scenarios.stream()
                .mapToLong(s -> minCostToReach(s.prize.x, s.prize.y, s.a.x, s.a.y, s.a.c, s.b.x, s.b.y, s.b.c))
                .filter(moves -> moves != UNREACHABLE)
                .sum();
    }

    private static ArrayList<Scenario> getScenarios(List<String> input) {
        var scenarios = new ArrayList<Scenario>();
        System.out.println(input);
        for (int i = 0; i < input.size(); i += 4) {
            var a = parseButton(input.get(i));
            var b = parseButton(input.get(i + 1));
            var p = parsePrize(input.get(i + 2));
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

    static Point parsePrize(String line) {
        System.out.println("parsePrize: " + line);
        var m = prizePattern.matcher(line);
        if (!m.find()) throw new IllegalStateException("invalid regex");
        return new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
    }

    static long minCostToReach(int pa, int pb, int x1, int y1, int c1, int x2, int y2, int c2) {
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

    static long minCostToReach2(int pa, int pb, int x1, int y1, int c1, int x2, int y2, int c2) {
        final var modX = Math.max(x1, x2);
        final var modY = Math.max(y1, y2);
        final var dp = new long[modX][modY];
        for (var d: dp) {
            Arrays.fill(d, UNREACHABLE);
        }
        dp[0][0] = 0;
        var changed = true;
        while (changed) {
            changed = false;
            for (int a = 0; a < modX; a++) {
                for (int b = 0; b < modY; b++) {
                    if (dp[a][b] == UNREACHABLE) continue;

                    var abCost = dp[a][b];
                    var newA = (a + x1) % modX;
                    var newB = (b + y1) % modY;
                    var newCost = abCost + c1;
                    if (newCost < dp[newA][newB]) {
                        dp[newA][newB] = newCost;
                        changed = true;
                    }

                    newA = (a + x2) % modX;
                    newB = (b + y2) % modY;
                    newCost = abCost + c2;
                    if (newCost < dp[newA][newB]) {
                        dp[newA][newB] = newCost;
                        changed = true;
                    }
                }
            }
        }

        final var targetRemX = pa % modX;
        final var targetRemY = pb % modY;
        return dp[targetRemX][targetRemY];
    }

    static long part2(String path) {
        var input = Util.loadFile(path);
        var scenarios = getScenarios(input);
        return scenarios.stream()
                .mapToLong(s -> minCostToReach2(s.prize.x, s.prize.y, s.a.x, s.a.y, s.a.c, s.b.x, s.b.y, s.b.c))
                .filter(moves -> moves != UNREACHABLE)
                .sum();
    }

    record Point(int x, int y) {
    }

    record Scenario(Point prize, Button a, Button b) {
    }

    record Button(char label, int x, int y, int c) {
    }
}
