package com.ianxc.aoc.util;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.OutputStream;
import java.io.PrintStream;

public class SuppressPrintExtension implements BeforeAllCallback, AfterAllCallback {
    private static final String SUPPRESS_PRINT = "SUPPRESS_PRINT";
    private static final PrintStream nullPrintStream = new PrintStream(OutputStream.nullOutputStream());

    private final boolean printSuppressedByEnv;
    private PrintStream originalPrintStream = null;

    private static boolean isPrintSuppressedByEnv() {
        var debugEnabledValue = System.getenv(SUPPRESS_PRINT);
        return debugEnabledValue != null && (
                debugEnabledValue.equalsIgnoreCase("true")
                        || debugEnabledValue.equalsIgnoreCase("yes")
                        || debugEnabledValue.equals("1")
        );
    }

    public SuppressPrintExtension() {
        this.printSuppressedByEnv = isPrintSuppressedByEnv();
    }

    @Override
    public void beforeAll(ExtensionContext ctx) {
        originalPrintStream = System.out;
        var suppressed = ctx.getTestClass()
                .map(testClass -> {
                    var annotation = testClass.getAnnotation(WithSilencePrintOutput.class);
                    return annotation != null && this.printSuppressedByEnv;
                })
                .orElseThrow();
        if (suppressed) {
            System.setOut(nullPrintStream);
        }
    }

    @Override
    public void afterAll(ExtensionContext ctx) {
        System.setOut(originalPrintStream);
    }
}
