package com.m450.chess.cli;

import com.m450.chess.engine.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MoveParser {
    private static final Pattern SQUARE = Pattern.compile("(?i)([A-J](?:10|[1-9]))");

    private MoveParser() {
    }

    public static Move parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Move input is null");
        }

        Matcher matcher = SQUARE.matcher(input.trim());
        List<String> squares = new ArrayList<>(2);
        while (matcher.find()) {
            squares.add(matcher.group(1));
        }

        if (squares.size() != 2) {
            throw new IllegalArgumentException(
                    "Invalid move format. Expected two squares like 'A2 A3', 'A2-A3', or 'e2e4'.");
        }

        Position from = Position.fromAlgebraic(squares.get(0));
        Position to = Position.fromAlgebraic(squares.get(1));
        return new Move(from, to);
    }
}
