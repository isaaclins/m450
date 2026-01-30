package com.m450.chess.engine;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void initialSetupHasAllVariantPieces() {
        Board board = Board.initialSetup();

        assertThat(board.countPieces(Color.WHITE).get(PieceType.ROOK)).isEqualTo(3);
        assertThat(board.countPieces(Color.WHITE).get(PieceType.LOVER)).isEqualTo(1);
        assertThat(board.countPieces(Color.WHITE).get(PieceType.PAWN)).isEqualTo(10);

        assertThat(board.countPieces(Color.BLACK).get(PieceType.ROOK)).isEqualTo(3);
        assertThat(board.countPieces(Color.BLACK).get(PieceType.LOVER)).isEqualTo(1);
        assertThat(board.countPieces(Color.BLACK).get(PieceType.PAWN)).isEqualTo(10);
    }

    @Test
    void extraRookAndLoverPositionsMatchSpec() {
        Board board = Board.initialSetup();

        assertThat(board.pieceAt(Position.fromAlgebraic("A1")).type()).isEqualTo(PieceType.LOVER);
        assertThat(board.pieceAt(Position.fromAlgebraic("B1")).type()).isEqualTo(PieceType.ROOK);
        assertThat(board.pieceAt(Position.fromAlgebraic("J1")).type()).isEqualTo(PieceType.ROOK);

        assertThat(board.pieceAt(Position.fromAlgebraic("A10")).type()).isEqualTo(PieceType.ROOK);
        assertThat(board.pieceAt(Position.fromAlgebraic("J10")).type()).isEqualTo(PieceType.LOVER);
    }

    @Test
    void queenCannotJumpOverPawns() {
        Board board = Board.initialSetup();
        boolean moved = board.move(Position.fromAlgebraic("E1"), Position.fromAlgebraic("E3"));
        assertThat(moved).isFalse();
    }

    @Test
    void pawnCapturesDiagonallyOnly() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E2"), new Piece(Color.WHITE, PieceType.PAWN));
        map.put(Position.fromAlgebraic("F3"), new Piece(Color.BLACK, PieceType.PAWN));
        Board board = new Board(map, Color.WHITE);

        boolean capture = board.move(Position.fromAlgebraic("E2"), Position.fromAlgebraic("F3"));
        assertThat(capture).isTrue();
        assertThat(board.captured()).hasSize(1);
    }

    @Test
    void rookBlockedByPieceOnPath() {
        Board board = Board.initialSetup();
        boolean moved = board.move(Position.fromAlgebraic("B1"), Position.fromAlgebraic("B4"));
        assertThat(moved).isFalse();
    }

    @Test
    void kingCannotMoveIntoCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("E3"), new Piece(Color.BLACK, PieceType.ROOK));
        Board board = new Board(map, Color.WHITE);

        boolean legal = board.move(Position.fromAlgebraic("E1"), Position.fromAlgebraic("E2"));
        assertThat(legal).isFalse();
        assertThat(board.currentPlayer()).isEqualTo(Color.WHITE);
    }

    @Test
    void bishopLimitedToSixSquares() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("C1"), new Piece(Color.WHITE, PieceType.BISHOP));
        Board board = new Board(map, Color.WHITE);

        boolean tooFar = board.move(Position.fromAlgebraic("C1"), Position.fromAlgebraic("J8"));
        assertThat(tooFar).isFalse();

        boolean withinRange = board.move(Position.fromAlgebraic("C1"), Position.fromAlgebraic("I7"));
        assertThat(withinRange).isTrue();
    }

    @Test
    void knightUsesThreeForwardOneSideRelativeToColor() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("C10"), new Piece(Color.BLACK, PieceType.KNIGHT));
        Board board = new Board(map, Color.BLACK);

        boolean move = board.move(Position.fromAlgebraic("C10"), Position.fromAlgebraic("B7"));
        assertThat(move).isTrue();
        assertThat(board.pieceAt(Position.fromAlgebraic("B7")).type()).isEqualTo(PieceType.KNIGHT);
    }

    @Test
    void isKingInCheckDetectsThreat() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("E4"), new Piece(Color.BLACK, PieceType.ROOK));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
    }

    @Test
    void cannotCaptureOwnPiece() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E2"), new Piece(Color.WHITE, PieceType.PAWN));
        map.put(Position.fromAlgebraic("E3"), new Piece(Color.WHITE, PieceType.ROOK));
        Board board = new Board(map, Color.WHITE);

        boolean legal = board.move(Position.fromAlgebraic("E2"), Position.fromAlgebraic("E3"));
        assertThat(legal).isFalse();
    }

    @Test
    void pawnForwardRequiresEmptySquare() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("D2"), new Piece(Color.WHITE, PieceType.PAWN));
        Board board = new Board(map, Color.WHITE);

        boolean forward = board.move(Position.fromAlgebraic("D2"), Position.fromAlgebraic("D3"));
        assertThat(forward).isTrue();
    }

    @Test
    void pawnCanAdvanceTwoSquaresFromStartIfClear() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("D2"), new Piece(Color.WHITE, PieceType.PAWN));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("D2"), Position.fromAlgebraic("D4"));
        assertThat(moved).isTrue();
    }

    @Test
    void pawnTwoSquareAdvanceRequiresClearIntermediateSquare() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("D2"), new Piece(Color.WHITE, PieceType.PAWN));
        map.put(Position.fromAlgebraic("D3"), new Piece(Color.BLACK, PieceType.PAWN));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("D2"), Position.fromAlgebraic("D4"));
        assertThat(moved).isFalse();
    }

    @Test
    void pawnBlockedCannotAdvance() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("D2"), new Piece(Color.WHITE, PieceType.PAWN));
        map.put(Position.fromAlgebraic("D3"), new Piece(Color.BLACK, PieceType.PAWN));
        Board board = new Board(map, Color.WHITE);

        boolean forward = board.move(Position.fromAlgebraic("D2"), Position.fromAlgebraic("D3"));
        assertThat(forward).isFalse();
    }

    @Test
    void queenMovesUpToBoardHeight() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.QUEEN));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("A1"), Position.fromAlgebraic("A10"));
        assertThat(moved).isTrue();
    }

    @Test
    void loverMovesLikeKing() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("B2"), new Piece(Color.WHITE, PieceType.LOVER));
        Board board = new Board(map, Color.WHITE);

        boolean step = board.move(Position.fromAlgebraic("B2"), Position.fromAlgebraic("C3"));
        assertThat(step).isTrue();
    }

    @Test
    void moveFromEmptySquareIsRejected() {
        Board board = Board.initialSetup();
        boolean moved = board.move(Position.fromAlgebraic("A5"), Position.fromAlgebraic("A6"));
        assertThat(moved).isFalse();
    }

    @Test
    void kingMovesOneStep() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E1"), new Piece(Color.WHITE, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("E1"), Position.fromAlgebraic("E2"));
        assertThat(moved).isTrue();
    }

    @Test
    void blockedLinePreventsCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("E4"), new Piece(Color.BLACK, PieceType.ROOK));
        map.put(Position.fromAlgebraic("E2"), new Piece(Color.WHITE, PieceType.PAWN)); // blocks rook
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
    }

    @Test
    void queenMovesDiagonallyWhenPathClear() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("B2"), new Piece(Color.WHITE, PieceType.QUEEN));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("B2"), Position.fromAlgebraic("H8"));
        assertThat(moved).isTrue();
    }

    @Test
    void wrongPlayerCannotMove() {
        Board board = Board.initialSetup();
        boolean moved = board.move(Position.fromAlgebraic("A10"), Position.fromAlgebraic("A9")); // black piece but
                                                                                                 // white to move
        assertThat(moved).isFalse();
    }

    @Test
    void sameSquareMoveIsIllegal() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("C3"), new Piece(Color.WHITE, PieceType.ROOK));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("C3"), Position.fromAlgebraic("C3"));
        assertThat(moved).isFalse();
    }

    @Test
    void knightCanGiveCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("D1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("C4"), new Piece(Color.BLACK, PieceType.KNIGHT));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
    }

    @Test
    void bishopCanGiveCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E3"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("H6"), new Piece(Color.BLACK, PieceType.BISHOP));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
    }

    @Test
    void pawnCanGiveCheckDiagonally() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("F2"), new Piece(Color.BLACK, PieceType.PAWN));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
    }

    @Test
    void loverCannotMoveMoreThanOne() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("B2"), new Piece(Color.WHITE, PieceType.LOVER));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("B2"), Position.fromAlgebraic("B4"));
        assertThat(moved).isFalse();
    }

    @Test
    void rookCapturesHorizontallyWhenPathClear() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.ROOK));
        map.put(Position.fromAlgebraic("E1"), new Piece(Color.BLACK, PieceType.BISHOP));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("A1"), Position.fromAlgebraic("E1"));
        assertThat(moved).isTrue();
        assertThat(board.captured()).hasSize(1);
    }

    @Test
    void queenCanGiveCheckDiagonally() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E5"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.QUEEN));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
    }

    @Test
    void kingAdjacentCountsAsCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E5"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("E6"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
    }

    @Test
    void loverCanGiveCheckLikeKing() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E5"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("F6"), new Piece(Color.BLACK, PieceType.LOVER));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
    }

    @Test
    void kingMayMoveIntoSquareAttackedByLover() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E5"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("G7"), new Piece(Color.BLACK, PieceType.LOVER)); // attacks F6
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("E5"), Position.fromAlgebraic("F6"));
        assertThat(moved).isTrue();
    }

    @Test
    void knightInvalidMoveRejected() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("C5"), new Piece(Color.WHITE, PieceType.KNIGHT));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("C5"), Position.fromAlgebraic("C7"));
        assertThat(moved).isFalse();
    }

    @Test
    void whiteKnightOneForwardThreeSide() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("D1"), new Piece(Color.WHITE, PieceType.KNIGHT));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("D1"), Position.fromAlgebraic("G2"));
        assertThat(moved).isTrue();
    }

    @Test
    void kingCannotLeapTwoSquares() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E1"), new Piece(Color.WHITE, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("E1"), Position.fromAlgebraic("E3"));
        assertThat(moved).isFalse();
    }

    @Test
    void queenBlockedDoesNotGiveCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("E3"), new Piece(Color.BLACK, PieceType.QUEEN)); // not aligned for attack
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
    }

    @Test
    void pawnWrongDirectionDoesNotAttackKing() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E3"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.BLACK, PieceType.PAWN)); // two ranks behind, wrong
                                                                                       // direction
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
    }

    @Test
    void rookCannotMoveDiagonally() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.ROOK));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("A1"), Position.fromAlgebraic("B2"));
        assertThat(moved).isFalse();
    }

    @Test
    void bishopMustStayOnDiagonal() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("C1"), new Piece(Color.WHITE, PieceType.BISHOP));
        Board board = new Board(map, Color.WHITE);

        boolean moved = board.move(Position.fromAlgebraic("C1"), Position.fromAlgebraic("C3"));
        assertThat(moved).isFalse();
    }

    @Test
    void missingKingReturnsNoCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.ROOK));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
    }

    @Test
    void moveWithNullDestinationIsIllegal() {
        Board board = Board.initialSetup();
        boolean moved = board.move(Position.fromAlgebraic("A2"), null);
        assertThat(moved).isFalse();
    }

    @Test
    void moveWithNullSourceIsIllegal() {
        Board board = Board.initialSetup();
        boolean moved = board.move(null, Position.fromAlgebraic("A3"));
        assertThat(moved).isFalse();
    }

    @Test
    void nonAttackingPiecesDoNotCheckKing() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("E5"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("A10"), new Piece(Color.BLACK, PieceType.ROOK)); // far file
        map.put(Position.fromAlgebraic("B2"), new Piece(Color.BLACK, PieceType.BISHOP)); // off diagonal
        map.put(Position.fromAlgebraic("C3"), new Piece(Color.BLACK, PieceType.KNIGHT)); // not attacking
        map.put(Position.fromAlgebraic("J1"), new Piece(Color.BLACK, PieceType.QUEEN)); // not aligned
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.BLACK, PieceType.LOVER)); // too far
        map.put(Position.fromAlgebraic("H1"), new Piece(Color.BLACK, PieceType.KING)); // too far
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.PAWN)); // wrong direction
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
    }

    @Test
    void rookAttackBlockedByOwnPiece() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("A4"), new Piece(Color.BLACK, PieceType.ROOK));
        map.put(Position.fromAlgebraic("A2"), new Piece(Color.BLACK, PieceType.PAWN)); // block
        map.put(Position.fromAlgebraic("A3"), new Piece(Color.BLACK, PieceType.LOVER)); // extra block
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
    }

    @Test
    void isCheckmateWhenInCheckAndNoLegalMoves() {
        // White king trapped in corner and checked by black rook.
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("A2"), new Piece(Color.BLACK, PieceType.ROOK)); // check
        map.put(Position.fromAlgebraic("B2"), new Piece(Color.BLACK, PieceType.QUEEN)); // controls B1
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.BLACK, PieceType.ROOK)); // blocks escape
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
        assertThat(board.isCheckmate(Color.WHITE)).isTrue();
    }

    @Test
    void isStalemateWhenNotInCheckAndNoLegalMoves() {
        // White king has no legal moves but is not in check.
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("B3"), new Piece(Color.BLACK, PieceType.QUEEN)); // covers A2/B2
        map.put(Position.fromAlgebraic("C2"), new Piece(Color.BLACK, PieceType.ROOK)); // covers B2
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isFalse();
        assertThat(board.isStalemate(Color.WHITE)).isTrue();
    }

    @Test
    void loverSideCastlingMovesKingTwoTowardRookAndRookNextToKing() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.LOVER));
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.WHITE, PieceType.ROOK));
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        boolean castled = board.move(Position.fromAlgebraic("F1"), Position.fromAlgebraic("D1"));
        assertThat(castled).isTrue();
        assertThat(board.pieceAt(Position.fromAlgebraic("D1")).type()).isEqualTo(PieceType.KING);
        assertThat(board.pieceAt(Position.fromAlgebraic("E1")).type()).isEqualTo(PieceType.ROOK);
        assertThat(board.pieceAt(Position.fromAlgebraic("B1"))).isNull();
    }

    @Test
    void castlingIsRejectedIfLoverHasMoved() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.LOVER).markMoved());
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.WHITE, PieceType.ROOK));
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.move(Position.fromAlgebraic("F1"), Position.fromAlgebraic("D1"))).isFalse();
    }

    @Test
    void castlingIsRejectedIfKingPassesThroughAttackedSquare() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.LOVER));
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.WHITE, PieceType.ROOK));
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("E4"), new Piece(Color.BLACK, PieceType.ROOK)); // attacks E1
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.move(Position.fromAlgebraic("F1"), Position.fromAlgebraic("D1"))).isFalse();
    }

    @Test
    void castlingIsRejectedIfRookHasMoved() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.LOVER));
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.WHITE, PieceType.ROOK).markMoved());
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.move(Position.fromAlgebraic("F1"), Position.fromAlgebraic("D1"))).isFalse();
    }

    @Test
    void castlingIsRejectedIfPieceBetweenKingAndRook() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.LOVER));
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.WHITE, PieceType.ROOK));
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("C1"), new Piece(Color.WHITE, PieceType.BISHOP));
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.move(Position.fromAlgebraic("F1"), Position.fromAlgebraic("D1"))).isFalse();
    }

    @Test
    void castlingIsRejectedIfKingIsCurrentlyInCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.LOVER));
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.WHITE, PieceType.ROOK));
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("F4"), new Piece(Color.BLACK, PieceType.ROOK)); // attacks F1
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.isKingInCheck(Color.WHITE)).isTrue();
        assertThat(board.move(Position.fromAlgebraic("F1"), Position.fromAlgebraic("D1"))).isFalse();
    }

    @Test
    void castlingIsOnlyAllowedTowardLoverSide() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(Position.fromAlgebraic("A1"), new Piece(Color.WHITE, PieceType.LOVER));
        map.put(Position.fromAlgebraic("B1"), new Piece(Color.WHITE, PieceType.ROOK));
        map.put(Position.fromAlgebraic("F1"), new Piece(Color.WHITE, PieceType.KING));
        map.put(Position.fromAlgebraic("J10"), new Piece(Color.BLACK, PieceType.KING));
        Board board = new Board(map, Color.WHITE);

        assertThat(board.move(Position.fromAlgebraic("F1"), Position.fromAlgebraic("H1"))).isFalse();
    }
}
