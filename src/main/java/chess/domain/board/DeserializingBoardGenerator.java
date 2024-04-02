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
        Map<Character, Piece> pieces = new HashMap<>();
        putWhitePieces(pieces);
        putBlackPieces(pieces);
        return pieces;
    }

    private static void putWhitePieces(Map<Character, Piece> pieces) {
        pieces.put('b', Bishop.withColor(WHITE));
        pieces.put('q', Queen.withColor(WHITE));
        pieces.put('r', Rook.withColor(WHITE));
        pieces.put('k', King.withColor(WHITE));
        pieces.put('n', Knight.withColor(WHITE));
        pieces.put('.', NoPiece.getInstance());
        pieces.put('p', Pawn.withColor(WHITE));
    }

    private void putBlackPieces(Map<Character, Piece> pieces) {
        pieces.put('B', Bishop.withColor(BLACK));
        pieces.put('Q', Queen.withColor(BLACK));
        pieces.put('R', Rook.withColor(BLACK));
        pieces.put('K', King.withColor(BLACK));
        pieces.put('N', Knight.withColor(BLACK));
        pieces.put('.', NoPiece.getInstance());
        pieces.put('P', Pawn.withColor(BLACK));
    }
}
