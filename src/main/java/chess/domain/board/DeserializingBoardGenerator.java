package chess.domain.board;

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
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class DeserializingBoardGenerator implements BoardGenerator {

    private final String boardText;

    public DeserializingBoardGenerator(String boardText) {
        this.boardText = boardText;
    }

    @Override
    public Map<Position, Piece> generate() {
        Map<Position, Piece> board = new HashMap<>();
        char[] pieceTexts = boardText.toCharArray();
        for (int i = 0; i < pieceTexts.length; i++) {
            Piece piece = toPiece(pieceTexts[i]);
            board.put(Position.of(i % 8 + 1, i / 8 + 1), piece);
        }
        return board;
    }

    private Piece toPiece(char pieceText) {
        return toPieceMap().get(pieceText);
    }

    private Map<Character, Piece> toPieceMap() {
        Map<Character, Piece> toPieceMap = new HashMap<>();
        putWhitePieces(toPieceMap);
        putBlackPieces(toPieceMap);
        return toPieceMap;
    }

    private static void putWhitePieces(Map<Character, Piece> toPieceMap) {
        toPieceMap.put('b', Bishop.withColor(WHITE));
        toPieceMap.put('q', Queen.withColor(WHITE));
        toPieceMap.put('r', Rook.withColor(WHITE));
        toPieceMap.put('k', King.withColor(WHITE));
        toPieceMap.put('n', Knight.withColor(WHITE));
        toPieceMap.put('.', NoPiece.getInstance());
        toPieceMap.put('p', Pawn.withColor(WHITE));
    }

    private void putBlackPieces(Map<Character, Piece> toPieceMap) {
        toPieceMap.put('B', Bishop.withColor(BLACK));
        toPieceMap.put('Q', Queen.withColor(BLACK));
        toPieceMap.put('R', Rook.withColor(BLACK));
        toPieceMap.put('K', King.withColor(BLACK));
        toPieceMap.put('N', Knight.withColor(BLACK));
        toPieceMap.put('.', NoPiece.getInstance());
        toPieceMap.put('P', Pawn.withColor(BLACK));
    }
}
