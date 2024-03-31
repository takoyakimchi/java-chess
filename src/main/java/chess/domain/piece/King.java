package chess.domain.piece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import chess.domain.position.Position;
import java.util.Map;

public class King extends Piece {

    private static final double KING_SCORE = 0.0;

    private static final King WHITE_KING = new King(WHITE);
    private static final King BLACK_KING = new King(BLACK);

    private King(Color color) {
        super(color);
    }

    public static King withColor(Color color) {
        if (color == WHITE) {
            return WHITE_KING;
        }
        return BLACK_KING;
    }

    @Override
    public boolean canMove(Position source, Position target, Map<Position, Piece> board) {
        int fileDifference = source.calculateFileDifference(target);
        int rankDifference = source.calculateRankDifference(target);

        if (Math.abs(fileDifference) == 1 && Math.abs(rankDifference) <= 1
            || Math.abs(fileDifference) <= 1 && Math.abs(rankDifference) == 1) {
            return board.get(target).isEmpty() || board.get(target).hasOppositeColorFrom(this);
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public double score() {
        return KING_SCORE;
    }

}
