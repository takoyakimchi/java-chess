package chess.domain.piece;

import static chess.domain.piece.Color.NO_COLOR;

import chess.domain.position.Position;
import java.util.Map;

public class NoPiece extends Piece {

    private static final NoPiece INSTANCE = new NoPiece();

    private NoPiece() {
        super(NO_COLOR);
    }

    public static NoPiece getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canMove(Position source, Position target, Map<Position, Piece> board) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public double score() {
        return 0.0;
    }
}
