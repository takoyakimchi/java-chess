package chess.domain.board;

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

public class InitialBoardGenerator implements BoardGenerator {

    @Override
    public Map<Position, Piece> generate() {
        Map<Position, Piece> board = new HashMap<>();
        placePawns(board);
        placeRooks(board);
        placeKnights(board);
        placeBishops(board);
        placeQueens(board);
        placeKings(board);
        placeNoPieces(board);
        return board;
    }

    private void placePawns(Map<Position, Piece> board) {
        for (int i = 1; i <= 8; i++) {
            board.put(Position.of(i, 2), Pawn.withColor(Color.WHITE));
            board.put(Position.of(i, 7), Pawn.withColor(Color.BLACK));
        }
    }

    private void placeRooks(Map<Position, Piece> board) {
        board.put(Position.of(1, 1), Rook.withColor(Color.WHITE));
        board.put(Position.of(8, 1), Rook.withColor(Color.WHITE));
        board.put(Position.of(1, 8), Rook.withColor(Color.BLACK));
        board.put(Position.of(8, 8), Rook.withColor(Color.BLACK));
    }

    private void placeKnights(Map<Position, Piece> board) {
        board.put(Position.of(2, 1), Knight.withColor(Color.WHITE));
        board.put(Position.of(7, 1), Knight.withColor(Color.WHITE));
        board.put(Position.of(2, 8), Knight.withColor(Color.BLACK));
        board.put(Position.of(7, 8), Knight.withColor(Color.BLACK));
    }

    private void placeBishops(Map<Position, Piece> board) {
        board.put(Position.of(3, 1), Bishop.withColor(Color.WHITE));
        board.put(Position.of(6, 1), Bishop.withColor(Color.WHITE));
        board.put(Position.of(3, 8), Bishop.withColor(Color.BLACK));
        board.put(Position.of(6, 8), Bishop.withColor(Color.BLACK));
    }

    private void placeQueens(Map<Position, Piece> board) {
        board.put(Position.of(4, 1), Queen.withColor(Color.WHITE));
        board.put(Position.of(4, 8), Queen.withColor(Color.BLACK));
    }

    private void placeKings(Map<Position, Piece> board) {
        board.put(Position.of(5, 8), King.withColor(Color.BLACK));
        board.put(Position.of(5, 1), King.withColor(Color.WHITE));
    }

    private void placeNoPieces(Map<Position, Piece> board) {
        for (int file = 1; file <= 8; file++) {
            board.put(Position.of(file, 3), NoPiece.getInstance());
            board.put(Position.of(file, 4), NoPiece.getInstance());
            board.put(Position.of(file, 5), NoPiece.getInstance());
            board.put(Position.of(file, 6), NoPiece.getInstance());
        }
    }
}
