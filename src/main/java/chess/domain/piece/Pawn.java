package chess.domain.piece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import chess.domain.position.Position;
import java.util.Map;

public class Pawn extends Piece {

    private static final Pawn WHITE_PAWN = new Pawn(WHITE);
    private static final Pawn BLACK_PAWN = new Pawn(BLACK);

    private Pawn(Color color) {
        super(color);
    }

    public static Pawn withColor(Color color) {
        if (color == WHITE) {
            return WHITE_PAWN;
        }
        return BLACK_PAWN;
    }

    @Override
    public boolean canMove(Position source, Position target, Map<Position, Piece> board) {
        if (target.equals(source.forward(color()))) {
            return board.get(target).isEmpty();
        }
        if (target.equals(source.forward(color()).forward(color()))) {
            return board.get(source.forward(color())).isEmpty() && board.get(target).isEmpty();
        }
        if (target.equals(source.forward(color()).left()) || target.equals(source.forward(color()).right())) {
            return board.get(target).hasOppositeColorFrom(this);
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public double score() {
        return 1.0;
    }

    @Override
    public String text() {
        String text = "P";
        if (color() == WHITE) {
            text = text.toLowerCase();
        }
        return text;
    }
}
