package chess.domain.piece;

import chess.domain.game.Score;
import chess.domain.position.Position;
import java.util.Map;

public class NoPiece extends Piece {

    public NoPiece(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position target, Map<Position, Piece> board) {
        return false;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public Score score() {
        return Score.valueOf(0.0);
    }
}
