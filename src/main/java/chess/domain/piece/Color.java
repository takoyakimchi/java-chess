package chess.domain.piece;

public enum Color {

    WHITE, BLACK;

    public static Color nextColorOf(Color previousColor) {
        if (previousColor == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
}