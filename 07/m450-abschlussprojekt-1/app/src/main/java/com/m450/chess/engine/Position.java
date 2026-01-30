package com.m450.chess.engine;

import java.util.Objects;

public final class Position {
    private final int file; // 0-9 (A-J)
    private final int rank; // 0-9 (1-10)

    private Position(int file, int rank) {
        if (file < 0 || file >= 10 || rank < 0 || rank >= 10) {
            throw new IllegalArgumentException("Position out of bounds: " + file + "," + rank);
        }
        this.file = file;
        this.rank = rank;
    }

    public static Position of(int file, int rank) {
        return new Position(file, rank);
    }

    public static Position fromAlgebraic(String notation) {
        if (notation == null || notation.length() < 2 || notation.length() > 3) {
            throw new IllegalArgumentException("Invalid notation: " + notation);
        }
        char fileChar = Character.toUpperCase(notation.charAt(0));
        int file = fileChar - 'A';
        int rank = Integer.parseInt(notation.substring(1)) - 1;
        return new Position(file, rank);
    }

    public int file() {
        return file;
    }

    public int rank() {
        return rank;
    }

    public String toAlgebraic() {
        return "" + (char) ('A' + file) + (rank + 1);
    }

    public Position translate(int df, int dr) {
        int nf = file + df;
        int nr = rank + dr;
        if (nf < 0 || nf >= 10 || nr < 0 || nr >= 10) {
            return null;
        }
        return new Position(nf, nr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return file == position.file && rank == position.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    @Override
    public String toString() {
        return toAlgebraic();
    }
}
