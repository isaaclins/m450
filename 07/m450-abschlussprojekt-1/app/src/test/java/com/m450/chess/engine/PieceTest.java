package com.m450.chess.engine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Test
    void markMovedReturnsSameWhenAlreadyMoved() {
        Piece moved = new Piece(Color.WHITE, PieceType.ROOK).markMoved();
        Piece again = moved.markMoved();
        assertThat(again).isSameAs(moved);
    }

    @Test
    void displaySymbolUsesUnicodeByColorAndType() {
        assertThat(new Piece(Color.WHITE, PieceType.KING).displaySymbol()).isEqualTo("♔");
        assertThat(new Piece(Color.BLACK, PieceType.KING).displaySymbol()).isEqualTo("♚");

        assertThat(new Piece(Color.WHITE, PieceType.QUEEN).displaySymbol()).isEqualTo("♕");
        assertThat(new Piece(Color.BLACK, PieceType.QUEEN).displaySymbol()).isEqualTo("♛");

        assertThat(new Piece(Color.WHITE, PieceType.ROOK).displaySymbol()).isEqualTo("♖");
        assertThat(new Piece(Color.BLACK, PieceType.ROOK).displaySymbol()).isEqualTo("♜");

        assertThat(new Piece(Color.WHITE, PieceType.BISHOP).displaySymbol()).isEqualTo("♗");
        assertThat(new Piece(Color.BLACK, PieceType.BISHOP).displaySymbol()).isEqualTo("♝");

        assertThat(new Piece(Color.WHITE, PieceType.KNIGHT).displaySymbol()).isEqualTo("♘");
        assertThat(new Piece(Color.BLACK, PieceType.KNIGHT).displaySymbol()).isEqualTo("♞");

        assertThat(new Piece(Color.WHITE, PieceType.PAWN).displaySymbol()).isEqualTo("♙");
        assertThat(new Piece(Color.BLACK, PieceType.PAWN).displaySymbol()).isEqualTo("♟");

        assertThat(new Piece(Color.WHITE, PieceType.LOVER).displaySymbol()).isEqualTo("♡");
        assertThat(new Piece(Color.BLACK, PieceType.LOVER).displaySymbol()).isEqualTo("♥");
    }
}
