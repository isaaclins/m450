package com.m450.chess.engine;

public enum Color {
    WHITE,
    BLACK;

    public int forwardStep() {
        return this == WHITE ? 1 : -1;
    }
}
