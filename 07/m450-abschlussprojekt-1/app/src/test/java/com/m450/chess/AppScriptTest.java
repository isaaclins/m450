package com.m450.chess;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AppScriptTest {
    @Test
    void scriptModeReplaysMovesAndPrintsBoard() throws Exception {
        Path script = Files.createTempFile("m450-chess-script-", ".txt");
        Files.writeString(script, "A2 A3\nA9 A8\nquit\n");

        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream previousOut = System.out;
        try {
            System.setOut(new PrintStream(outBytes));
            App.main(new String[] { "--script", script.toString() });
        } finally {
            System.setOut(previousOut);
        }

        String out = outBytes.toString();
        assertThat(out).contains("Script:");
        assertThat(out).contains("== Step");
        assertThat(out).contains("QUIT");
    }

    @Test
    void scriptModeSupportsNewGameAndSkipsComments() throws Exception {
        Path script = Files.createTempFile("m450-chess-script-", ".txt");
        Files.writeString(script, "# comment\n\nnew game\nA2 A3\nquit\n");

        String out = runMainCapturingStdout(new String[] { "--script", script.toString() });

        assertThat(out).contains("NEW GAME");
        assertThat(out).contains("== Step 1: new game ==");
        assertThat(out).contains("== Step 2: A2 A3 ==");
    }

    @Test
    void scriptModeReportsInvalidMoveFormat() throws Exception {
        Path script = Files.createTempFile("m450-chess-script-", ".txt");
        Files.writeString(script, "not a move\n");

        String out = runMainCapturingStdout(new String[] { "--script", script.toString() });
        assertThat(out).contains("Invalid move at line 1");
        assertThat(out).contains("Invalid move format");
    }

    @Test
    void scriptModeReportsIllegalMoves() throws Exception {
        Path script = Files.createTempFile("m450-chess-script-", ".txt");
        // White pawn cannot move backwards.
        Files.writeString(script, "A2 A1\n");

        String out = runMainCapturingStdout(new String[] { "--script", script.toString() });
        assertThat(out).contains("Illegal move at line 1");
    }

    @Test
    void scriptModeReportsMissingScript() {
        String missing = "missing-" + UUID.randomUUID() + ".txt";
        String out = runMainCapturingStdout(new String[] { "--script", missing });
        assertThat(out).contains("Failed to read script");
    }

    @Test
    void scriptModeResolvesRelativePathByWalkingUpFromUserDir() throws Exception {
        Path root = Files.createTempDirectory("m450-chess-root-");
        Path scriptsDir = Files.createDirectories(root.resolve("scripts"));
        Path appDir = Files.createDirectories(root.resolve("app"));
        Path script = scriptsDir.resolve("walk-up.txt");
        Files.writeString(script, "A2 A3\nquit\n");

        String oldUserDir = System.getProperty("user.dir");
        try {
            System.setProperty("user.dir", appDir.toString());
            String out = runMainCapturingStdout(new String[] { "--script", "scripts/walk-up.txt" });
            assertThat(out).contains("Script:");
            assertThat(out).contains("== Step 1: A2 A3 ==");
        } finally {
            if (oldUserDir != null) {
                System.setProperty("user.dir", oldUserDir);
            }
        }
    }

    private static String runMainCapturingStdout(String[] args) {
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream previousOut = System.out;
        try {
            System.setOut(new PrintStream(outBytes));
            App.main(args);
        } finally {
            System.setOut(previousOut);
        }
        return outBytes.toString();
    }
}
