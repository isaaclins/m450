package com.m450.chess;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class AppMainTest {

    @Test
    void mainPrintsBoard() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        try {
            System.setIn(new java.io.ByteArrayInputStream("10\nquit\n".getBytes()));
            App.main(new String[] {});
        } finally {
            System.setOut(original);
            System.setIn(originalIn);
        }
        String output = out.toString();
        assertThat(output).contains("Turn: WHITE");
        assertThat(output).contains("10");
    }

    @Test
    void mainAcceptsNewGameCommand() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        try {
            System.setIn(new java.io.ByteArrayInputStream("10\nnew game\nquit\n".getBytes()));
            App.main(new String[] {});
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        String output = out.toString();
        assertThat(output).contains("Turn: WHITE");
    }

    @Test
    void mainDefaultsToTenMinutesOnEmptyInput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        try {
            System.setIn(new java.io.ByteArrayInputStream("\nquit\n".getBytes()));
            App.main(new String[] {});
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        assertThat(out.toString()).contains("Time - WHITE: 10:00");
    }

    @Test
    void mainRepromptsOnInvalidTimeSelection() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        try {
            System.setIn(new java.io.ByteArrayInputStream("7\n5\nquit\n".getBytes()));
            App.main(new String[] {});
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        String output = out.toString();
        assertThat(output).contains("Invalid time");
        assertThat(output).contains("Time - WHITE: 5:00");
    }

    @Test
    void mainPrintsIllegalMoveMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        try {
            // A2->A1 is a valid format but illegal pawn move.
            System.setIn(new java.io.ByteArrayInputStream("10\nA2 A1\nquit\n".getBytes()));
            App.main(new String[] {});
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        assertThat(out.toString()).contains("Illegal move.");
    }

    @Test
    void mainPrintsInvalidMoveFormatMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        try {
            System.setIn(new java.io.ByteArrayInputStream("10\nnot a move\nquit\n".getBytes()));
            App.main(new String[] {});
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        assertThat(out.toString()).contains("Invalid move format");
    }

    @Test
    void mainPrintsCapturedPiecesWhenAnyCaptureHappens() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        try {
            // Create a quick capture:
            // 1) White: C1 D4 (knight)
            // 1... Black: C9 C7 (pawn, 2-step)
            // 2) White: D4 C7 (knight captures that pawn)
            System.setIn(new java.io.ByteArrayInputStream(
                    ("10\n" +
                            "C1 D4\n" +
                            "C9 C7\n" +
                            "D4 C7\n" +
                            "quit\n").getBytes()));
            App.main(new String[] {});
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        assertThat(out.toString()).contains("Captured:");
    }
}
