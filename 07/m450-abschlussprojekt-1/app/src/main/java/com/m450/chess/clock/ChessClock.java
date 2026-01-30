package com.m450.chess.clock;

import com.m450.chess.engine.Color;

import java.util.Objects;
import java.util.function.LongSupplier;

public final class ChessClock {
    private final LongSupplier nowMillis;
    private long whiteRemaining;
    private long blackRemaining;
    private Color active;
    private long lastTimestamp;

    public ChessClock(int minutes, LongSupplier nowMillis) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("Minutes must be positive");
        }
        this.nowMillis = Objects.requireNonNull(nowMillis, "nowMillis");
        long start = minutes * 60_000L;
        this.whiteRemaining = start;
        this.blackRemaining = start;
        this.active = null;
        this.lastTimestamp = 0;
    }

    public void start(Color startingPlayer) {
        this.active = Objects.requireNonNull(startingPlayer, "startingPlayer");
        this.lastTimestamp = nowMillis.getAsLong();
    }

    public Color activeColor() {
        return active;
    }

    public long remainingMillis(Color color) {
        return color == Color.WHITE ? whiteRemaining : blackRemaining;
    }

    public boolean isFlagged(Color color) {
        return remainingMillis(color) <= 0;
    }

    public void tick() {
        if (active == null) {
            return;
        }
        long now = nowMillis.getAsLong();
        long delta = now - lastTimestamp;
        if (delta <= 0) {
            lastTimestamp = now;
            return;
        }

        if (active == Color.WHITE) {
            whiteRemaining -= delta;
        } else {
            blackRemaining -= delta;
        }
        lastTimestamp = now;
    }

    public void switchTo(Color newActive) {
        tick();
        this.active = Objects.requireNonNull(newActive, "newActive");
        this.lastTimestamp = nowMillis.getAsLong();
    }
}
