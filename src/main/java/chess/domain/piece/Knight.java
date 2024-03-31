package chess.domain.piece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import chess.domain.position.Position;
import java.util.Map;

public class Knight extends Piece {

    private static final double KNIGHT_SCORE = 2.5;

    private static final Knight WHITE_KNIGHT = new Knight(WHITE);
    private static final Knight BLACK_KNIGHT = new Knight(BLACK);

    private Knight(Color color) {
        super(color);
    }

    public static Knight withColor(Color color) {
        if (color == WHITE) {
            return WHITE_KNIGHT;
        }
        return BLACK_KNIGHT;
    }

    @Override
    public boolean canMove(Position source, Position target, Map<Position, Piece> board) {
        int fileDifference = source.calculateFileDifference(target);
        int rankDifference = source.calculateRankDifference(target);

        if ((Math.abs(fileDifference) == 1 && Math.abs(rankDifference) == 2)
            || (Math.abs(fileDifference) == 2 && Math.abs(rankDifference) == 1)) {
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
        return KNIGHT_SCORE;
    }

}
