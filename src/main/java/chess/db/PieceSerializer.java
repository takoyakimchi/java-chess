package chess.db;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.NoPiece;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.slidingpiece.Bishop;
import chess.domain.piece.slidingpiece.Queen;
import chess.domain.piece.slidingpiece.Rook;
import java.util.HashMap;
import java.util.Map;

public class PieceSerializer {

    private final Map<Piece, String> pieceTextMap;

    public PieceSerializer(Map<Piece, String> pieceTextMap) {
        this.pieceTextMap = pieceTextMap;
    }

    public static PieceSerializer initialize() {
        Map<Piece, String> pieceTextMap = new HashMap<>();
        putWhitePieces(pieceTextMap);
        putBlackPieces(pieceTextMap);
        pieceTextMap.put(NoPiece.getInstance(), ".");
        return new PieceSerializer(pieceTextMap);
    }

    private static void putWhitePieces(Map<Piece, String> map) {
        map.put(Bishop.withColor(WHITE), "b");
        map.put(Queen.withColor(WHITE), "q");
        map.put(Rook.withColor(WHITE), "r");
        map.put(King.withColor(WHITE), "k");
        map.put(Knight.withColor(WHITE), "n");
        map.put(Pawn.withColor(WHITE), "p");
    }

    private static void putBlackPieces(Map<Piece, String> map) {
        map.put(Bishop.withColor(BLACK), "B");
        map.put(Queen.withColor(BLACK), "Q");
        map.put(Rook.withColor(BLACK), "R");
        map.put(King.withColor(BLACK), "K");
        map.put(Knight.withColor(BLACK), "N");
        map.put(Pawn.withColor(BLACK), "P");
    }

    public String textOf(Piece piece) {
        try {
            return pieceTextMap.get(piece);
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("올바르지 않은 기물입니다.");
        }
    }
}
