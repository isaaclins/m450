package com.m450.chess.clock;

import com.m450.chess.engine.Color;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessClockTest {

    @Test
    void startsWithSameTimeForBothPlayers() {
        AtomicLong now = new AtomicLong(1_000);
        ChessClock clock = new ChessClock(10, now::get);
        clock.start(Color.WHITE);

        assertThat(clock.remainingMillis(Color.WHITE)).isEqualTo(10 * 60_000L);
        assertThat(clock.remainingMillis(Color.BLACK)).isEqualTo(10 * 60_000L);
    }

    @Test
    void tickingConsumesActivePlayersTime() {
        AtomicLong now = new AtomicLong(1_000);
        ChessClock clock = new ChessClock(5, now::get);
        clock.start(Color.WHITE);

        now.addAndGet(2_500);
        clock.tick();

        assertThat(clock.remainingMillis(Color.WHITE)).isEqualTo(5 * 60_000L - 2_500);
        assertThat(clock.remainingMillis(Color.BLACK)).isEqualTo(5 * 60_000L);
    }

    @Test
    void switchingTurnTicksThenChangesActiveColor() {
        AtomicLong now = new AtomicLong(1_000);
        ChessClock clock = new ChessClock(5, now::get);
        clock.start(Color.WHITE);

        now.addAndGet(1_000);
        clock.switchTo(Color.BLACK);

        assertThat(clock.activeColor()).isEqualTo(Color.BLACK);
        assertThat(clock.remainingMillis(Color.WHITE)).isEqualTo(5 * 60_000L - 1_000);

        now.addAndGet(2_000);
        clock.tick();
        assertThat(clock.remainingMillis(Color.BLACK)).isEqualTo(5 * 60_000L - 2_000);
    }

    @Test
    void flagsWhenTimeRunsOut() {
        AtomicLong now = new AtomicLong(1_000);
        ChessClock clock = new ChessClock(5, now::get);
        clock.start(Color.WHITE);

        now.addAndGet(5 * 60_000L + 1);
        clock.tick();

        assertThat(clock.isFlagged(Color.WHITE)).isTrue();
        assertThat(clock.isFlagged(Color.BLACK)).isFalse();
    }

    @Test
    void rejectsInvalidMinutes() {
        AtomicLong now = new AtomicLong(1_000);
        assertThatThrownBy(() -> new ChessClock(0, now::get))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
