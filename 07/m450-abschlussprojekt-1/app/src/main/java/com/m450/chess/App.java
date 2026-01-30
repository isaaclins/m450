package com.m450.chess;

import com.m450.chess.cli.Move;
import com.m450.chess.cli.MoveParser;
import com.m450.chess.clock.ChessClock;
import com.m450.chess.engine.Board;
import com.m450.chess.engine.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class App {
    public static void main(String[] args) {
        if (args != null && args.length >= 2 && args[0].equals("--script")) {
            runScript(Path.of(args[1]));
            return;
        }

        Board board = Board.initialSetup();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int minutes = promptMinutes(in);
        ChessClock clock = new ChessClock(minutes, System::currentTimeMillis);
        clock.start(Color.WHITE);

        System.out.println();

        while (true) {
            clearScreen();
            System.out.println(board.render());
            Color current = board.currentPlayer();

            System.out.println("Time - WHITE: " + formatMillis(clock.remainingMillis(Color.WHITE))
                    + ", BLACK: " + formatMillis(clock.remainingMillis(Color.BLACK)));

            if (board.isCheckmate(current)) {
                System.out.println("CHECKMATE - Winner: " + (current == Color.WHITE ? Color.BLACK : Color.WHITE));
                break;
            }
            if (board.isStalemate(current)) {
                System.out.println("DRAW - Stalemate");
                break;
            }
            if (board.isKingInCheck(current)) {
                System.out.println("CHECK");
            }

            if (!board.captured().isEmpty()) {
                System.out.println("Captured: " + board.captured().stream().map(p -> p.displaySymbol()).toList());
            }

            System.out.print("Enter move (e.g. A2 A3) or command (quit, new game): ");
            String line;
            try {
                line = in.readLine();
            } catch (IOException e) {
                System.out.println("Input error: " + e.getMessage());
                break;
            }
            if (line == null) {
                break;
            }

            clock.tick();
            if (clock.isFlagged(current)) {
                System.out.println("TIMEOUT - Winner: " + (current == Color.WHITE ? Color.BLACK : Color.WHITE));
                break;
            }

            String trimmed = line.trim();
            if (trimmed.equalsIgnoreCase("quit")) {
                break;
            }
            if (trimmed.equalsIgnoreCase("new game")) {
                board = Board.initialSetup();
                clock = new ChessClock(minutes, System::currentTimeMillis);
                clock.start(Color.WHITE);
                System.out.println();
                continue;
            }

            try {
                Move move = MoveParser.parse(trimmed);
                boolean ok = board.move(move.from(), move.to());
                if (!ok) {
                    System.out.println("Illegal move.");
                } else {
                    clock.switchTo(board.currentPlayer());
                }
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void runScript(Path scriptPath) {
        Path resolvedPath = resolveScriptPath(scriptPath);
        if (resolvedPath == null) {
            System.out.println("Failed to read script: " + scriptPath);
            return;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(resolvedPath);
        } catch (IOException e) {
            System.out.println("Failed to read script: " + resolvedPath + " (" + e.getMessage() + ")");
            return;
        }

        Board board = Board.initialSetup();
        System.out.println("Script: " + resolvedPath);

        int step = 0;

        for (int i = 0; i < lines.size(); i++) {
            String raw = lines.get(i);
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            step++;

            System.out.println();
            System.out.println("== Step " + step + ": " + line + " ==");

            if (line.equalsIgnoreCase("quit")) {
                System.out.println("QUIT");
                break;
            }
            if (line.equalsIgnoreCase("new game")) {
                board = Board.initialSetup();
                System.out.println("NEW GAME");
                System.out.println(board.render());
                continue;
            }

            try {
                Move move = MoveParser.parse(line);
                boolean ok = board.move(move.from(), move.to());
                if (!ok) {
                    System.out.println("Illegal move at line " + (i + 1) + ": " + raw);
                    break;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid move at line " + (i + 1) + ": " + ex.getMessage());
                break;
            }

            System.out.println(board.render());

            Color current = board.currentPlayer();
            if (board.isCheckmate(current)) {
                System.out.println("CHECKMATE - Winner: " + (current == Color.WHITE ? Color.BLACK : Color.WHITE));
                break;
            }
            if (board.isStalemate(current)) {
                System.out.println("DRAW - Stalemate");
                break;
            }
            if (board.isKingInCheck(current)) {
                System.out.println("CHECK");
            }
        }
    }

    private static Path resolveScriptPath(Path scriptPath) {
        if (scriptPath == null) {
            return null;
        }
        if (Files.isRegularFile(scriptPath)) {
            return scriptPath;
        }

        // Under Gradle, the application working directory can be the module folder
        // (e.g. `app/`).
        // If a relative path was provided, try walking up a few levels to find the
        // script.
        if (!scriptPath.isAbsolute()) {
            Path base = Path.of(System.getProperty("user.dir"));
            Path cursor = base;
            for (int i = 0; i < 4; i++) {
                Path candidate = cursor.resolve(scriptPath).normalize();
                if (Files.isRegularFile(candidate)) {
                    return candidate;
                }
                cursor = cursor.getParent();
                if (cursor == null) {
                    break;
                }
            }
        }
        return null;
    }

    private static void clearScreen() {
        // ANSI clear screen + cursor home.
        System.out.print("\u001B[H\u001B[2J");
        System.out.flush();
    }

    private static int promptMinutes(BufferedReader in) {
        while (true) {
            System.out.print("Select time (5/10/20 minutes) [default 10]: ");
            String line;
            try {
                line = in.readLine();
            } catch (IOException e) {
                return 10;
            }
            if (line == null) {
                return 10;
            }
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                return 10;
            }
            if (trimmed.equals("5") || trimmed.equals("10") || trimmed.equals("20")) {
                return Integer.parseInt(trimmed);
            }
            System.out.println("Invalid time. Please choose 5, 10, or 20.");
        }
    }

    private static String formatMillis(long millis) {
        long clamped = Math.max(0, millis);
        long totalSeconds = clamped / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
