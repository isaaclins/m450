package com.m450.chess.engine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {

    @Test
    void convertsToAndFromAlgebraic() {
        Position pos = Position.fromAlgebraic("J10");
        assertThat(pos.file()).isEqualTo(9);
        assertThat(pos.rank()).isEqualTo(9);
        assertThat(pos.toAlgebraic()).isEqualTo("J10");
    }

    @Test
    void translateReturnsNullOutOfBounds() {
        Position edge = Position.fromAlgebraic("A1");
        assertThat(edge.translate(-1, 0)).isNull();
    }

    @Test
    void rejectsInvalidNotation() {
        assertThatThrownBy(() -> Position.fromAlgebraic("Z99"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsOutOfBoundsCoordinates() {
        assertThatThrownBy(() -> Position.of(-1, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Position.of(0, 10)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void equalsHandlesDifferentType() {
        Position pos = Position.fromAlgebraic("A1");
        assertThat(pos.equals("A1")).isFalse();
    }

    @Test
    void translateInsideBoard() {
        Position pos = Position.fromAlgebraic("B2");
        Position moved = pos.translate(1, 1);
        assertThat(moved).isNotNull();
        assertThat(moved.toAlgebraic()).isEqualTo("C3");
    }
}
