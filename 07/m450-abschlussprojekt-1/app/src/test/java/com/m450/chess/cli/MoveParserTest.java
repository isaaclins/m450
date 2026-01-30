package com.m450.chess.cli;

import com.m450.chess.engine.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoveParserTest {

    @Test
    void parsesSpaceSeparated() {
        Move move = MoveParser.parse("A2 A3");
        assertThat(move.from()).isEqualTo(Position.fromAlgebraic("A2"));
        assertThat(move.to()).isEqualTo(Position.fromAlgebraic("A3"));
    }

    @Test
    void parsesDashed() {
        Move move = MoveParser.parse("A2-A3");
        assertThat(move.from()).isEqualTo(Position.fromAlgebraic("A2"));
        assertThat(move.to()).isEqualTo(Position.fromAlgebraic("A3"));
    }

    @Test
    void parsesConcatenatedAndLowercase() {
        Move move = MoveParser.parse("e2e4");
        assertThat(move.from()).isEqualTo(Position.fromAlgebraic("E2"));
        assertThat(move.to()).isEqualTo(Position.fromAlgebraic("E4"));
    }

    @Test
    void parsesRankTenSquares() {
        Move move = MoveParser.parse("J10 A9");
        assertThat(move.from()).isEqualTo(Position.fromAlgebraic("J10"));
        assertThat(move.to()).isEqualTo(Position.fromAlgebraic("A9"));
    }

    @Test
    void rejectsNullInput() {
        assertThatThrownBy(() -> MoveParser.parse(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsInputWithoutTwoSquares() {
        assertThatThrownBy(() -> MoveParser.parse("A2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expected two squares");

        assertThatThrownBy(() -> MoveParser.parse("A2 A3 A4"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expected two squares");
    }
}
