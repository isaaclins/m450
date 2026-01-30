package com.m450.chess.engine;

public final class Piece {
    private final Color color;
    private final PieceType type;
    private final boolean moved;

    public Piece(Color color, PieceType type) {
        this(color, type, false);
    }

    private Piece(Color color, PieceType type, boolean moved) {
        this.color = color;
        this.type = type;
        this.moved = moved;
    }

    public Color color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public boolean moved() {
        return moved;
    }

    public Piece markMoved() {
        if (moved)
            return this;
        return new Piece(color, type, true);
    }

    public String displaySymbol() {
        return switch (type) {
            case KING -> color == Color.WHITE ? "♔" : "♚";
            case QUEEN -> color == Color.WHITE ? "♕" : "♛";
            case ROOK -> color == Color.WHITE ? "♖" : "♜";
            case BISHOP -> color == Color.WHITE ? "♗" : "♝";
            case KNIGHT -> color == Color.WHITE ? "♘" : "♞";
            case PAWN -> color == Color.WHITE ? "♙" : "♟";
            case LOVER -> color == Color.WHITE ? "♡" : "♥";
        };
    }
}
