package chess.domain.game;

public enum WinStatus {

    WHITE_WIN,
    BLACK_WIN,
    DRAW,
    ;

    public static WinStatus decideWinStatus(double whiteScore, double blackScore) {
        if (Double.compare(whiteScore, blackScore) == 0) {
            return DRAW;
        }
        if (whiteScore > blackScore) {
            return WHITE_WIN;
        }
        return BLACK_WIN;
    }
}
