package com.m450.chess.engine;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final Map<Position, Piece> pieces;
    private final List<Piece> captured;
    private Color currentPlayer;

    public Board(Map<Position, Piece> pieces, Color currentPlayer) {
        this.pieces = new HashMap<>(pieces);
        this.captured = new ArrayList<>();
        this.currentPlayer = currentPlayer;
    }

    public static Board initialSetup() {
        Map<Position, Piece> positions = new HashMap<>();

        // White back rank (rank 0)
        positions.put(Position.of(0, 0), new Piece(Color.WHITE, PieceType.LOVER));
        positions.put(Position.of(1, 0), new Piece(Color.WHITE, PieceType.ROOK));
        positions.put(Position.of(2, 0), new Piece(Color.WHITE, PieceType.KNIGHT));
        positions.put(Position.of(3, 0), new Piece(Color.WHITE, PieceType.BISHOP));
        positions.put(Position.of(4, 0), new Piece(Color.WHITE, PieceType.QUEEN));
        positions.put(Position.of(5, 0), new Piece(Color.WHITE, PieceType.KING));
        positions.put(Position.of(6, 0), new Piece(Color.WHITE, PieceType.BISHOP));
        positions.put(Position.of(7, 0), new Piece(Color.WHITE, PieceType.KNIGHT));
        positions.put(Position.of(8, 0), new Piece(Color.WHITE, PieceType.ROOK));
        positions.put(Position.of(9, 0), new Piece(Color.WHITE, PieceType.ROOK));

        // White pawns (rank 1)
        for (int file = 0; file < 10; file++) {
            positions.put(Position.of(file, 1), new Piece(Color.WHITE, PieceType.PAWN));
        }

        // Black back rank (rank 9) mirrored with lover on J10, extra rook on A10
        positions.put(Position.of(0, 9), new Piece(Color.BLACK, PieceType.ROOK));
        positions.put(Position.of(1, 9), new Piece(Color.BLACK, PieceType.ROOK));
        positions.put(Position.of(2, 9), new Piece(Color.BLACK, PieceType.KNIGHT));
        positions.put(Position.of(3, 9), new Piece(Color.BLACK, PieceType.BISHOP));
        positions.put(Position.of(4, 9), new Piece(Color.BLACK, PieceType.QUEEN));
        positions.put(Position.of(5, 9), new Piece(Color.BLACK, PieceType.KING));
        positions.put(Position.of(6, 9), new Piece(Color.BLACK, PieceType.BISHOP));
        positions.put(Position.of(7, 9), new Piece(Color.BLACK, PieceType.KNIGHT));
        positions.put(Position.of(8, 9), new Piece(Color.BLACK, PieceType.ROOK));
        positions.put(Position.of(9, 9), new Piece(Color.BLACK, PieceType.LOVER));

        // Black pawns (rank 8)
        for (int file = 0; file < 10; file++) {
            positions.put(Position.of(file, 8), new Piece(Color.BLACK, PieceType.PAWN));
        }

        return new Board(positions, Color.WHITE);
    }

    public Color currentPlayer() {
        return currentPlayer;
    }

    public Piece pieceAt(Position pos) {
        return pieces.get(pos);
    }

    public List<Piece> captured() {
        return Collections.unmodifiableList(captured);
    }

    public boolean move(Position from, Position to) {
        if (!isLegalMove(from, to)) {
            return false;
        }

        applyUnchecked(from, to);
        return true;
    }

    public boolean isLegalMove(Position from, Position to) {
        Piece moving = pieces.get(from);
        if (moving == null || moving.color() != currentPlayer) {
            return false;
        }
        if (from.equals(to) || !isInside(to))
            return false;
        Piece occupant = pieces.get(to);
        if (occupant != null && occupant.color() == moving.color()) {
            return false;
        }

        if (!isValidPattern(from, to, moving)) {
            return false;
        }

        // Ensure king not left in check
        Board clone = this.copy();
        clone.applyUnchecked(from, to);
        return !clone.isKingInCheck(moving.color());
    }

    private void applyUnchecked(Position from, Position to) {
        Piece moving = pieces.get(from);

        if (moving != null && moving.type() == PieceType.KING && isLoverSideCastlingMove(from, to, moving)) {
            applyLoverSideCastleUnchecked(from, to, moving);
            return;
        }

        pieces.remove(from);
        Piece capturedPiece = pieces.remove(to);
        if (capturedPiece != null) {
            captured.add(capturedPiece);
        }
        pieces.put(to, moving.markMoved());
        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private void applyLoverSideCastleUnchecked(Position from, Position to, Piece king) {
        // King moves 2 squares towards the Lover-side rook (file B); rook lands next to
        // the king.
        int rank = from.rank();
        Position rookFrom = Position.of(1, rank); // B-file
        Position rookTo = Position.of(to.file() + 1, rank); // immediately right of king destination

        Piece rook = pieces.get(rookFrom);

        pieces.remove(from);
        pieces.remove(rookFrom);
        pieces.put(to, king.markMoved());
        pieces.put(rookTo, rook.markMoved());

        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private Board copy() {
        Map<Position, Piece> copy = new HashMap<>(pieces);
        Board b = new Board(copy, currentPlayer);
        b.captured.addAll(this.captured);
        return b;
    }

    private boolean isInside(Position pos) {
        return pos != null && pos.file() >= 0 && pos.file() < 10 && pos.rank() >= 0 && pos.rank() < 10;
    }

    private boolean isValidPattern(Position from, Position to, Piece piece) {
        int df = to.file() - from.file();
        int dr = to.rank() - from.rank();
        int adf = Math.abs(df);
        int adr = Math.abs(dr);

        switch (piece.type()) {
            case KING:
                if (isLoverSideCastlingMove(from, to, piece)) {
                    return isLoverSideCastlingLegal(from, to, piece);
                }
                return adf <= 1 && adr <= 1;
            case LOVER:
                return adf <= 1 && adr <= 1;
            case QUEEN:
                return (df == 0 || dr == 0 || adf == adr) && pathClear(from, to, 10);
            case ROOK:
                return (df == 0 || dr == 0) && pathClear(from, to, 10);
            case BISHOP:
                return adf == adr && adf > 0 && adf <= 6 && pathClear(from, to, 6);
            case KNIGHT:
                int forward = piece.color() == Color.WHITE ? 1 : -1;
                if (dr == 3 * forward && adf == 1)
                    return true;
                if (dr == 1 * forward && adf == 3)
                    return true;
                return false;
            case PAWN:
                int step = piece.color().forwardStep();
                if (df == 0 && dr == step && pieces.get(to) == null)
                    return true;
                if (df == 0 && dr == 2 * step && !piece.moved()) {
                    int startRank = piece.color() == Color.WHITE ? 1 : 8;
                    if (from.rank() != startRank)
                        return false;
                    Position mid = from.translate(0, step);
                    return mid != null && pieces.get(mid) == null && pieces.get(to) == null;
                }
                if (adf == 1 && dr == step && pieces.get(to) != null && pieces.get(to).color() != piece.color())
                    return true;
                return false;
            default:
                return false;
        }
    }

    private boolean isLoverSideCastlingMove(Position from, Position to, Piece king) {
        if (from == null || to == null || king == null)
            return false;
        if (king.type() != PieceType.KING)
            return false;
        return from.rank() == to.rank() && (to.file() - from.file()) == -2;
    }

    private boolean isLoverSideCastlingLegal(Position from, Position to, Piece king) {
        if (king.moved())
            return false;
        int rank = from.rank();

        Position loverPos = Position.of(0, rank); // A-file
        Position rookPos = Position.of(1, rank); // B-file

        Piece lover = pieces.get(loverPos);
        Piece rook = pieces.get(rookPos);

        if (lover == null || lover.type() != PieceType.LOVER || lover.color() != king.color() || lover.moved())
            return false;
        if (rook == null || rook.type() != PieceType.ROOK || rook.color() != king.color() || rook.moved())
            return false;

        // Squares between rook and king must be empty (C..E for standard start).
        int minFile = Math.min(rookPos.file(), from.file());
        int maxFile = Math.max(rookPos.file(), from.file());
        for (int file = minFile + 1; file < maxFile; file++) {
            Position mid = Position.of(file, rank);
            if (pieces.containsKey(mid))
                return false;
        }

        // King cannot be in check, and cannot pass through attacked squares.
        if (isKingInCheck(king.color()))
            return false;

        Color opponent = king.color() == Color.WHITE ? Color.BLACK : Color.WHITE;
        Position step1 = from.translate(-1, 0);
        if (step1 == null)
            return false;
        if (isSquareAttackedBy(step1, opponent))
            return false;
        if (isSquareAttackedBy(to, opponent))
            return false;

        return true;
    }

    private boolean isSquareAttackedBy(Position square, Color attacker) {
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Piece p = entry.getValue();
            if (p.color() != attacker)
                continue;
            if (p.type() == PieceType.LOVER)
                continue;
            if (attacks(entry.getKey(), square, p))
                return true;
        }
        return false;
    }

    private boolean pathClear(Position from, Position to, int maxDistance) {
        int df = Integer.compare(to.file(), from.file());
        int dr = Integer.compare(to.rank(), from.rank());
        int steps = Math.max(Math.abs(to.file() - from.file()), Math.abs(to.rank() - from.rank()));
        if (steps == 0 || steps > maxDistance) {
            return false;
        }
        Position cursor = from;
        for (int i = 1; i < steps; i++) {
            cursor = cursor.translate(df, dr);
            if (cursor == null)
                return false;
            if (pieces.containsKey(cursor))
                return false;
        }
        return true;
    }

    public boolean isKingInCheck(Color color) {
        Position kingPos = findKing(color);
        if (kingPos == null)
            return false;
        Color opponent = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            if (entry.getValue().color() != opponent)
                continue;
            if (entry.getValue().type() == PieceType.LOVER)
                continue;
            if (attacks(entry.getKey(), kingPos, entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(Color color) {
        return isKingInCheck(color) && !hasAnyLegalMove(color);
    }

    public boolean isStalemate(Color color) {
        return !isKingInCheck(color) && !hasAnyLegalMove(color);
    }

    private boolean hasAnyLegalMove(Color color) {
        Board probe = this.copy();
        probe.currentPlayer = color;

        for (Map.Entry<Position, Piece> entry : probe.pieces.entrySet()) {
            if (entry.getValue().color() != color)
                continue;
            Position from = entry.getKey();
            for (int file = 0; file < 10; file++) {
                for (int rank = 0; rank < 10; rank++) {
                    Position to = Position.of(file, rank);
                    if (probe.isLegalMove(from, to)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position findKing(Color color) {
        return pieces.entrySet().stream()
                .filter(e -> e.getValue().color() == color && e.getValue().type() == PieceType.KING)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private boolean attacks(Position from, Position target, Piece piece) {
        int df = target.file() - from.file();
        int dr = target.rank() - from.rank();
        int adf = Math.abs(df);
        int adr = Math.abs(dr);

        switch (piece.type()) {
            case KING:
            case LOVER:
                return adf <= 1 && adr <= 1;
            case QUEEN:
                if (!(df == 0 || dr == 0 || adf == adr))
                    return false;
                return pathClearForAttack(from, target, 10);
            case ROOK:
                if (!(df == 0 || dr == 0))
                    return false;
                return pathClearForAttack(from, target, 10);
            case BISHOP:
                if (!(adf == adr && adf > 0 && adf <= 6))
                    return false;
                return pathClearForAttack(from, target, 6);
            case KNIGHT:
                int forward = piece.color() == Color.WHITE ? 1 : -1;
                if (dr == 3 * forward && adf == 1)
                    return true;
                if (dr == 1 * forward && adf == 3)
                    return true;
                return false;
            case PAWN:
                int step = piece.color().forwardStep();
                return adf == 1 && dr == step;
            default:
                return false;
        }
    }

    private boolean pathClearForAttack(Position from, Position target, int maxDistance) {
        int df = Integer.compare(target.file(), from.file());
        int dr = Integer.compare(target.rank(), from.rank());
        int steps = Math.max(Math.abs(target.file() - from.file()), Math.abs(target.rank() - from.rank()));
        if (steps == 0 || steps > maxDistance)
            return false;
        Position cursor = from;
        for (int i = 1; i < steps; i++) {
            cursor = cursor.translate(df, dr);
            if (cursor == null)
                return false;
            if (pieces.containsKey(cursor))
                return false;
        }
        return true;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        for (int rank = 9; rank >= 0; rank--) {
            sb.append(String.format("%2d ", rank + 1));
            for (int file = 0; file < 10; file++) {
                Position pos = Position.of(file, rank);
                Piece p = pieces.get(pos);
                sb.append(p == null ? "." : p.displaySymbol());
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("   ");
        for (int file = 0; file < 10; file++) {
            sb.append((char) ('A' + file)).append(" ");
        }
        sb.append("\n");
        sb.append("Turn: ").append(currentPlayer);
        return sb.toString();
    }

    public Map<PieceType, Long> countPieces(Color color) {
        return pieces.values().stream()
                .filter(p -> p.color() == color)
                .collect(Collectors.groupingBy(Piece::type, Collectors.counting()));
    }
}
