package chess.domain.piece.slidingpiece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static chess.domain.piece.slidingpiece.Direction.DOWN;
import static chess.domain.piece.slidingpiece.Direction.DOWN_LEFT;
import static chess.domain.piece.slidingpiece.Direction.DOWN_RIGHT;
import static chess.domain.piece.slidingpiece.Direction.LEFT;
import static chess.domain.piece.slidingpiece.Direction.RIGHT;
import static chess.domain.piece.slidingpiece.Direction.UP;
import static chess.domain.piece.slidingpiece.Direction.UP_LEFT;
import static chess.domain.piece.slidingpiece.Direction.UP_RIGHT;

import chess.domain.piece.Color;
import java.util.Set;

public class Queen extends SlidingPiece {

    private static final double QUEEN_SCORE = 9.0;

    private static final Queen WHITE_QUEEN = new Queen(WHITE);
    private static final Queen BLACK_QUEEN = new Queen(BLACK);

    private Queen(Color color) {
        super(color);
    }

    public static Queen withColor(Color color) {
        if (color == WHITE) {
            return WHITE_QUEEN;
        }
        return BLACK_QUEEN;
    }

    @Override
    Set<Direction> directions() {
        return Set.of(UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, UP, DOWN, LEFT, RIGHT);
    }

    @Override
    public double score() {
        return QUEEN_SCORE;
    }

    @Override
    public String text() {
        String text = "Q";
        if (color() == WHITE) {
            text = text.toLowerCase();
        }
        return text;
    }
}
