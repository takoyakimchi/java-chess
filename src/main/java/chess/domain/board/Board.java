package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.NoPiece;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class Board {

    private static final double LOSE_SCORE = 0.0;
    private static final double PAWN_PENALTY_SCORE = 0.5;

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board generatedBy(BoardGenerator boardGenerator) {
        return new Board(boardGenerator.generate());
    }

    public void move(Position source, Position target, Color color) {
        Piece piece = board.get(source);
        validateMove(source, target, color);
        board.put(source, new NoPiece(Color.NO_COLOR));
        board.put(target, piece);
    }

    public Piece findPieceAt(Position position) {
        return board.get(position);
    }

    private void validateMove(Position source, Position target, Color color) {
        validateNoPieceAtSource(source);
        validateTurn(source, color);
        validatePieceRule(source, target);
    }

    private void validateNoPieceAtSource(Position source) {
        if (findPieceAt(source).isEmpty()) {
            throw new IllegalArgumentException("출발점에 말이 없습니다.");
        }
    }

    private void validateTurn(Position source, Color color) {
        if (findPieceAt(source).isNotColored(color)) {
            throw new IllegalArgumentException(String.format("%s이 움직일 차례입니다.", color.toString()));
        }
    }

    private void validatePieceRule(Position source, Position target) {
        if (findPieceAt(source).canMove(source, target, board)) {
            return;
        }
        throw new IllegalArgumentException("말의 규칙에 맞지 않는 이동입니다.");
    }

    public double totalScore(Color color) {
        if (!board.containsValue(new King(color))) {
            return LOSE_SCORE;
        }
        return generalScore(color) - penaltyScore(color);
    }

    private double generalScore(Color color) {
        return board.values()
            .stream()
            .filter(piece -> piece.isColored(color))
            .mapToDouble(Piece::score)
            .sum();
    }

    private double penaltyScore(Color color) {
        long penalizedPawnAmount = IntStream.rangeClosed(1, 8)
            .mapToLong(file -> pawnAmountOnFile(file, color))
            .filter(number -> number >= 2)
            .sum();
        return PAWN_PENALTY_SCORE * penalizedPawnAmount;
    }

    private long pawnAmountOnFile(int file, Color color) {
        return IntStream.rangeClosed(1, 8)
            .filter(rank -> findPieceAt(Position.of(file, rank)).equals(new Pawn(color)))
            .count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board1 = (Board) o;
        return Objects.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }
}
