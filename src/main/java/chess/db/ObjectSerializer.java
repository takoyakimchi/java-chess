package chess.db;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import chess.domain.board.Board;
import chess.domain.board.DeserializingBoardGenerator;
import chess.domain.piece.Color;
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

public class ObjectSerializer {

    private static final int MINIMUM_BOARD_INDEX = 1;
    private static final int MAXIMUM_BOARD_INDEX = 8;

    public String serializeBoard(Board board) {
        StringBuilder builder = new StringBuilder();
        for (int rank = MINIMUM_BOARD_INDEX; rank <= MAXIMUM_BOARD_INDEX; rank++) {
            builder.append(serializeRank(board, rank));
        }
        return builder.toString();
    }

    public Board deserializeBoard(String boardText) {
        return Board.generatedBy(new DeserializingBoardGenerator(boardText));
    }

    public String serializeColor(Color color) {
        return color.name();
    }

    public Color deserializeColor(String colorText) {
        return Color.valueOf(colorText);
    }

    private String serializeRank(Board board, int rank) {
        StringBuilder builder = new StringBuilder();
        for (int file = MINIMUM_BOARD_INDEX; file <= MAXIMUM_BOARD_INDEX; file++) {
            Piece piece = board.findPieceAt(Position.of(file, rank));
            builder.append(serializePiece(piece));
        }
        return builder.toString();
    }

    private String serializePiece(Piece piece) {
        Map<Piece, String> pieces = new HashMap<>();
        putWhitePieces(pieces);
        putBlackPieces(pieces);
        pieces.put(NoPiece.getInstance(), ".");

        try {
            return pieces.get(piece);
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("올바르지 않은 기물입니다.");
        }
    }

    private void putWhitePieces(Map<Piece, String> pieces) {
        pieces.put(Bishop.withColor(WHITE), "b");
        pieces.put(Queen.withColor(WHITE), "q");
        pieces.put(Rook.withColor(WHITE), "r");
        pieces.put(King.withColor(WHITE), "k");
        pieces.put(Knight.withColor(WHITE), "n");
        pieces.put(Pawn.withColor(WHITE), "p");
    }

    private void putBlackPieces(Map<Piece, String> pieces) {
        pieces.put(Bishop.withColor(BLACK), "B");
        pieces.put(Queen.withColor(BLACK), "Q");
        pieces.put(Rook.withColor(BLACK), "R");
        pieces.put(King.withColor(BLACK), "K");
        pieces.put(Knight.withColor(BLACK), "N");
        pieces.put(Pawn.withColor(BLACK), "P");
    }
}
