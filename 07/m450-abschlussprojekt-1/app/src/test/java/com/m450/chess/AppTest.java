package com.m450.chess;

import com.m450.chess.engine.Board;
import com.m450.chess.engine.Color;
import com.m450.chess.engine.Piece;
import com.m450.chess.engine.PieceType;
import com.m450.chess.engine.Position;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Test
    void initialBoardHasExpectedPieceCounts() {
        Board board = Board.initialSetup();
        assertThat(board.countPieces(Color.WHITE).get(PieceType.PAWN)).isEqualTo(10);
        assertThat(board.countPieces(Color.WHITE).get(PieceType.ROOK)).isEqualTo(3);
        assertThat(board.countPieces(Color.WHITE).get(PieceType.LOVER)).isEqualTo(1);
        assertThat(board.countPieces(Color.BLACK).get(PieceType.LOVER)).isEqualTo(1);
    }

    @Test
    void initialPositionsMatchSpec() {
        Board board = Board.initialSetup();
        Piece whiteLover = board.pieceAt(Position.fromAlgebraic("A1"));
        Piece whiteExtraRook = board.pieceAt(Position.fromAlgebraic("J1"));
        Piece blackLover = board.pieceAt(Position.fromAlgebraic("J10"));
        Piece blackExtraRook = board.pieceAt(Position.fromAlgebraic("A10"));

        assertThat(whiteLover.type()).isEqualTo(PieceType.LOVER);
        assertThat(whiteExtraRook.type()).isEqualTo(PieceType.ROOK);
        assertThat(blackLover.type()).isEqualTo(PieceType.LOVER);
        assertThat(blackExtraRook.type()).isEqualTo(PieceType.ROOK);
    }

    @Test
    void renderShowsTurn() {
        Board board = Board.initialSetup();
        assertThat(board.render()).contains("Turn: WHITE");
    }

    @Test
    void pawnsCannotMoveBackwards() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E3"), new Piece(Color.WHITE, PieceType.PAWN));
        Board board = new Board(map, Color.WHITE);

        boolean backwards = board.move(Position.fromAlgebraic("E3"), Position.fromAlgebraic("E2"));
        assertThat(backwards).isFalse();
    }
}
