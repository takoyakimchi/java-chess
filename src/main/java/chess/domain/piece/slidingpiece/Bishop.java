package chess.domain.piece.slidingpiece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static chess.domain.piece.slidingpiece.Direction.DOWN_LEFT;
import static chess.domain.piece.slidingpiece.Direction.DOWN_RIGHT;
import static chess.domain.piece.slidingpiece.Direction.UP_LEFT;
import static chess.domain.piece.slidingpiece.Direction.UP_RIGHT;

import chess.domain.piece.Color;
import java.util.Set;

public class Bishop extends SlidingPiece {

    private static final double BISHOP_SCORE = 3.0;

    private static final Bishop WHITE_BISHOP = new Bishop(WHITE);
    private static final Bishop BLACK_BISHOP = new Bishop(BLACK);

    private Bishop(Color color) {
        super(color);
    }

    public static Bishop withColor(Color color) {
        if (color == WHITE) {
            return WHITE_BISHOP;
        }
        return BLACK_BISHOP;
    }

    @Override
    Set<Direction> directions() {
        return Set.of(UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT);
    }

    @Override
    public double score() {
        return BISHOP_SCORE;
    }

    @Override
    public String text() {
        String text = "B";
        if (color() == WHITE) {
            text = text.toLowerCase();
        }
        return text;
    }
}
