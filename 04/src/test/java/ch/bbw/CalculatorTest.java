package ch.bbw;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// AUFGABE 1: entfernung der @Disabled
class CalculatorTest {
    Calculator calculator;

    @Test
    void add_1_plus_1() {
        int a = 1;
        int b = 1;

        int result = calculator.add(a, b);

        assertEquals(a + b, result, "addition failed");
    }
}
