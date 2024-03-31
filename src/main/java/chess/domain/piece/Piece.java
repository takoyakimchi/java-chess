package chess.domain.piece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static chess.domain.piece.Color.oppose;

import chess.domain.position.Position;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {

    private final Color color;

    protected Piece(Color color) {
        this.color = color;
    }

    public abstract boolean canMove(Position source, Position target, Map<Position, Piece> board);

    public abstract double score();

    public abstract String text();

    public boolean isEmpty() {
        return this.equals(NoPiece.getInstance());
    }

    public boolean isWhite() {
        return color == WHITE;
    }

    public boolean isColored(Color color) {
        return this.color == color;
    }

    public boolean isNotColored(Color color) {
        return this.color != color;
    }

    public boolean isKing() {
        return this.equals(King.withColor(WHITE)) || this.equals(King.withColor(BLACK));
    }

    public boolean hasOppositeColorFrom(Piece other) {
        return this.color == oppose(other.color);
    }

    protected Color color() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
