package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.NoPiece;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Map;
import java.util.stream.IntStream;

public class Board {

    private static final int MINIMUM_BOARD_INDEX = 1;
    private static final int MAXIMUM_BOARD_INDEX = 8;

    private static final double LOSE_SCORE = 0.0;
    private static final double PAWN_PENALTY_SCORE = 0.5;
    private static final int MINIMUM_PENALIZING_PAWN_AMOUNT = 2;

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board generatedBy(BoardGenerator boardGenerator) {
        return new Board(boardGenerator.generate());
    }

    public static Board initialize() {
        return new Board(new InitialBoardGenerator().generate());
    }

    public void move(Position source, Position target, Color color) {
        Piece piece = board.get(source);
        validateMove(source, target, color);
        board.put(source, NoPiece.getInstance());
        board.put(target, piece);
    }

    public Piece findPieceAt(Position position) {
        return board.get(position);
    }

    public long kingCount() {
        return board.values()
            .stream()
            .filter(Piece::isKing)
            .count();
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
        if (!board.containsValue(King.withColor(color))) {
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
        long penalizedPawnAmount = IntStream.rangeClosed(MINIMUM_BOARD_INDEX, MAXIMUM_BOARD_INDEX)
            .mapToLong(file -> pawnAmountOnFile(file, color))
            .filter(number -> number >= MINIMUM_PENALIZING_PAWN_AMOUNT)
            .sum();
        return PAWN_PENALTY_SCORE * penalizedPawnAmount;
    }

    private long pawnAmountOnFile(int file, Color color) {
        return IntStream.rangeClosed(MINIMUM_BOARD_INDEX, MAXIMUM_BOARD_INDEX)
            .filter(rank -> findPieceAt(Position.of(file, rank)).equals(Pawn.withColor(color)))
            .count();
    }
}
