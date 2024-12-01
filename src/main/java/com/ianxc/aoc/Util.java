package com.ianxc.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class Util {
    static List<String> loadFile(String path) {
        try (var stream = Util.class.getClassLoader().getResourceAsStream(path)) {
            var reader = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(stream), StandardCharsets.UTF_8));
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
