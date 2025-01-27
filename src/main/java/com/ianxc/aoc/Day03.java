package com.ianxc.aoc;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Day03 {

    static String r = "mul\\((\\d+),(\\d+)\\)";
    static Pattern p = Pattern.compile(r, Pattern.MULTILINE);

    static Pattern do2 = Pattern.compile("do\\(\\)");
    static Pattern dont2 = Pattern.compile("don't\\(\\)");
    static Comparator<DPoint> cmp = Comparator.comparing(DPoint::index);

    static int part1(String path) {
        var total = 0;
        for (var line : Util.loadFile(path)) {
            var matcher = p.matcher(line);
            while (matcher.find()) {
                var d1 = Integer.parseInt(matcher.group(1));
                var d2 = Integer.parseInt(matcher.group(2));
                System.out.printf("d1=%d, d2=%d\n", d1, d2);
                total += d1 * d2;
            }
        }
        return total;
    }

    static int part2(String path) {
        var total = 0;
        var mem = String.join("", Util.loadFile(path));

        var treeSet = new TreeSet<>(cmp);
        var doMatcher = do2.matcher(mem);
        while (doMatcher.find()) {
            treeSet.add(new DPoint(doMatcher.start(), true));
        }

        var dontMatcher = dont2.matcher(mem);
        while (dontMatcher.find()) {
            treeSet.add(new DPoint(dontMatcher.start(), false));
        }

        var matcher = p.matcher(mem);
        while (matcher.find()) {
            var x = treeSet.floor(new DPoint(matcher.start(), true));
            if (x == null || x.isDo()) {
                var d1 = Integer.parseInt(matcher.group(1));
                var d2 = Integer.parseInt(matcher.group(2));
                System.out.printf("do()    d1=%d, d2=%d\n", d1, d2);
                total += d1 * d2;
            } else {
                System.out.printf("don't() st=%d, en=%d\n", matcher.start(), matcher.end());
            }
        }
        return total;
    }

    record DPoint(int index, boolean isDo) {
    }
}
