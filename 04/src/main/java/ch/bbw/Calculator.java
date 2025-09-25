package ch.bbw;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        // BUG: should multiply
        return a + b;
    }

    public int divide(int a, int b) {
        return a / b;
    }
}