package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board generatedBy(BoardGenerator boardGenerator) {
        return new Board(boardGenerator.generate());
    }

    public void move(Position sourcePosition, Position targetPosition, Color color) {
        Piece piece = board.get(sourcePosition);
        validateMove(sourcePosition, targetPosition, color);
        board.remove(sourcePosition);
        board.put(targetPosition, piece);
    }

    private void validateMove(Position sourcePosition, Position targetPosition, Color color) {
        validateNoPieceAtSource(sourcePosition);
        validateTurn(sourcePosition, color);
        validateMoveToSamePosition(sourcePosition, targetPosition);
        validateOwnPieceAtTarget(sourcePosition, targetPosition);
        validatePieceRule(sourcePosition, targetPosition);
        validateRoute(sourcePosition, targetPosition);
        validatePawnMove(sourcePosition, targetPosition);
    }

    private void validateNoPieceAtSource(Position sourcePosition) {
        if (isNoPieceAt(sourcePosition)) {
            throw new IllegalArgumentException("출발점에 말이 없습니다.");
        }
    }

    private void validateTurn(Position sourcePosition, Color color) {
        Piece piece = board.get(sourcePosition);
        if (piece.hasColorOf(color)) {
            return;
        }
        throw new IllegalArgumentException("자신의 말만 움직일 수 있습니다.");
    }

    private void validateMoveToSamePosition(Position sourcePosition, Position targetPosition) {
        if (sourcePosition.equals(targetPosition)) {
            throw new IllegalArgumentException("출발점과 도착점은 같을 수 없습니다.");
        }
    }

    private void validateOwnPieceAtTarget(Position sourcePosition, Position targetPosition) {
        if (isPieceAt(targetPosition) && (findPieceColorAt(sourcePosition) == findPieceColorAt(targetPosition))) {
            throw new IllegalArgumentException("한 칸에 말이 2개 존재할 수 없습니다.");
        }
    }

    private void validatePieceRule(Position sourcePosition, Position targetPosition) {
        Piece piece = board.get(sourcePosition);
        if (piece.canMove(sourcePosition, targetPosition)) {
            return;
        }
        throw new IllegalArgumentException("말의 규칙에 맞지 않는 이동입니다.");
    }

    private void validateRoute(Position sourcePosition, Position targetPosition) {
        if (isStraightMove(sourcePosition, targetPosition) || isDiagonalMove(sourcePosition, targetPosition)) {
            validatePieceExistOnRoute(sourcePosition, targetPosition);
        }
    }

    private void validatePieceExistOnRoute(Position sourcePosition, Position targetPosition) {
        Direction direction = Direction.of(sourcePosition, targetPosition);
        Position currentPosition = sourcePosition.nextPosition(direction);
        while (!currentPosition.equals(targetPosition)) {
            validatePieceExistAt(currentPosition);
            currentPosition = currentPosition.nextPosition(direction);
        }
    }

    private void validatePieceExistAt(Position middlePosition) {
        if (isPieceAt(middlePosition)) {
            throw new IllegalArgumentException("경로에 말이 있으면 움직일 수 없습니다.");
        }
    }

    private void validatePawnMove(Position sourcePosition, Position targetPosition) {
        Piece piece = board.get(sourcePosition);
        if (piece.isPawn()) {
            validatePawnStraightCapture(sourcePosition, targetPosition);
            validatePawnDiagonalMove(sourcePosition, targetPosition);
        }
    }

    private void validatePawnStraightCapture(Position sourcePosition, Position targetPosition) {
        if (isStraightMove(sourcePosition, targetPosition) && isPieceAt(targetPosition)) {
            throw new IllegalArgumentException("직진으로 잡을 수 없습니다.");
        }
    }

    private void validatePawnDiagonalMove(Position sourcePosition, Position targetPosition) {
        if (isDiagonalMove(sourcePosition, targetPosition) && isNoPieceAt(targetPosition)) {
            throw new IllegalArgumentException("대각선 방향에 상대방 말이 없으면 움직일 수 없습니다.");
        }
    }

    private boolean isStraightMove(Position sourcePosition, Position targetPosition) {
        return sourcePosition.isOnSameRankAs(targetPosition)
            || sourcePosition.isOnSameFileAs(targetPosition);
    }

    private boolean isDiagonalMove(Position sourcePosition, Position targetPosition) {
        return sourcePosition.isOnSameDiagonalAs(targetPosition);
    }

    private boolean isPieceAt(Position position) {
        return board.containsKey(position);
    }

    private boolean isNoPieceAt(Position position) {
        return !board.containsKey(position);
    }

    private Color findPieceColorAt(Position position) {
        if (isNoPieceAt(position)) {
            throw new IllegalArgumentException("해당 위치에 말이 없습니다.");
        }
        if (board.get(position).isWhite()) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    public Piece findPieceAt(Position position) {
        if (isNoPieceAt(position)) {
            return null;
        }
        return board.get(position);
    }
}
