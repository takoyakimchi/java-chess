package chess.domain.piece.slidingpiece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static chess.domain.piece.slidingpiece.Direction.DOWN;
import static chess.domain.piece.slidingpiece.Direction.LEFT;
import static chess.domain.piece.slidingpiece.Direction.RIGHT;
import static chess.domain.piece.slidingpiece.Direction.UP;

import chess.domain.piece.Color;
import java.util.Set;

public class Rook extends SlidingPiece {

    private static final Rook WHITE_ROOK = new Rook(WHITE);
    private static final Rook BLACK_ROOK = new Rook(BLACK);

    private Rook(Color color) {
        super(color);
    }

    public static Rook withColor(Color color) {
        if (color == WHITE) {
            return WHITE_ROOK;
        }
        return BLACK_ROOK;
    }

    @Override
    Set<Direction> directions() {
        return Set.of(UP, DOWN, LEFT, RIGHT);
    }

    @Override
    public double score() {
        return 5.0;
    }

    @Override
    public String text() {
        String text = "R";
        if (color() == WHITE) {
            text = text.toLowerCase();
        }
        return text;
    }
}
