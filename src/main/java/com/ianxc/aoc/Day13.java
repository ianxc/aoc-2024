package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Day13 {
    private static final long UNREACHABLE = Long.MAX_VALUE / 2;
    private static final Pattern buttonPattern = Pattern.compile("Button ([A-Z]): X\\+(\\d+), Y\\+(\\d+)");
    private static final Pattern prizePattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    static long part1(String path) {
        var input = Util.loadFile(path);

        var scenarios = new ArrayList<Scenario>();
        System.out.println(input);
        for (int i = 0; i < input.size(); i += 4) {
            var a = parseButton(input.get(i));
            var b = parseButton(input.get(i + 1));
            var p = parsePrize(input.get(i + 2));
            scenarios.add(new Scenario(p, a, b));
        }
        System.out.println(scenarios);
        return scenarios.stream()
                .mapToLong(s -> minCostToReach(s.prize.x, s.prize.y, s.a.x, s.a.y, s.a.c, s.b.x, s.b.y, s.b.c))
                .filter(moves -> moves !=  UNREACHABLE)
                .sum();
    }

    static Button parseButton(String line) {
        System.out.println("parseButton: " + line);
        var m = buttonPattern.matcher(line);
        m.find();
        var label = m.group(1).charAt(0);
        var cost = label == 'A' ? 3 : 1;
        return new Button(label, Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), cost);
    }

    static Point parsePrize(String line) {
        System.out.println("parsePrize: " + line);
        var m = prizePattern.matcher(line);
        m.find();
        return new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
    }

    static long minCostToReach(int a, int b, int x1, int y1, int c1, int x2, int y2, int c2) {
        final var dp = new long[a + 1][b + 1];
        for (var row : dp) {
            Arrays.fill(row, UNREACHABLE);
        }

        dp[0][0] = 0;

        for (int i = 0; i <= a; i++) {
            for (int j = 0; j <= b; j++) {
                if (dp[i][j] == UNREACHABLE) {
                    continue;
                }

                final var xFromButton1 = i + x1;
                final var yFromButton1 = j + y1;
                if (xFromButton1 <= a && yFromButton1 <= b) {
                    final var newCost = dp[i][j] + c1;
                    dp[xFromButton1][yFromButton1] = Math.min(dp[xFromButton1][yFromButton1], newCost);
                }

                final var xFromButton2 = i + x2;
                final var yFromButton2 = j + y2;
                if (xFromButton2 <= a && yFromButton2 <= b) {
                    final var newCost = dp[i][j] + c2;
                    dp[xFromButton2][yFromButton2] = Math.min(dp[xFromButton2][yFromButton2], newCost);
                }
            }
        }

        return dp[a][b];
    }

    record Point(int x, int y) {
    }

    record Scenario(Point prize, Button a, Button b) {
    }

    record Button(char label, int x, int y, int c) {
    }
}
