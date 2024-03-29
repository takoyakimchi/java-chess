package chess.domain.piece;

import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class EmptyBoardGenerator {

    public static Map<Position, Piece> create() {
        Map<Position, Piece> boardMap = new HashMap<>();

        boardMap.put(Position.of(1, 1), NoPiece.getInstance());
        boardMap.put(Position.of(1, 2), NoPiece.getInstance());
        boardMap.put(Position.of(1, 3), NoPiece.getInstance());
        boardMap.put(Position.of(1, 4), NoPiece.getInstance());
        boardMap.put(Position.of(1, 5), NoPiece.getInstance());
        boardMap.put(Position.of(1, 6), NoPiece.getInstance());
        boardMap.put(Position.of(1, 7), NoPiece.getInstance());
        boardMap.put(Position.of(1, 8), NoPiece.getInstance());

        boardMap.put(Position.of(2, 1), NoPiece.getInstance());
        boardMap.put(Position.of(2, 2), NoPiece.getInstance());
        boardMap.put(Position.of(2, 3), NoPiece.getInstance());
        boardMap.put(Position.of(2, 4), NoPiece.getInstance());
        boardMap.put(Position.of(2, 5), NoPiece.getInstance());
        boardMap.put(Position.of(2, 6), NoPiece.getInstance());
        boardMap.put(Position.of(2, 7), NoPiece.getInstance());
        boardMap.put(Position.of(2, 8), NoPiece.getInstance());

        boardMap.put(Position.of(3, 1), NoPiece.getInstance());
        boardMap.put(Position.of(3, 2), NoPiece.getInstance());
        boardMap.put(Position.of(3, 3), NoPiece.getInstance());
        boardMap.put(Position.of(3, 4), NoPiece.getInstance());
        boardMap.put(Position.of(3, 5), NoPiece.getInstance());
        boardMap.put(Position.of(3, 6), NoPiece.getInstance());
        boardMap.put(Position.of(3, 7), NoPiece.getInstance());
        boardMap.put(Position.of(3, 8), NoPiece.getInstance());

        boardMap.put(Position.of(4, 1), NoPiece.getInstance());
        boardMap.put(Position.of(4, 2), NoPiece.getInstance());
        boardMap.put(Position.of(4, 3), NoPiece.getInstance());
        boardMap.put(Position.of(4, 4), NoPiece.getInstance());
        boardMap.put(Position.of(4, 5), NoPiece.getInstance());
        boardMap.put(Position.of(4, 6), NoPiece.getInstance());
        boardMap.put(Position.of(4, 7), NoPiece.getInstance());
        boardMap.put(Position.of(4, 8), NoPiece.getInstance());

        boardMap.put(Position.of(5, 1), NoPiece.getInstance());
        boardMap.put(Position.of(5, 2), NoPiece.getInstance());
        boardMap.put(Position.of(5, 3), NoPiece.getInstance());
        boardMap.put(Position.of(5, 4), NoPiece.getInstance());
        boardMap.put(Position.of(5, 5), NoPiece.getInstance());
        boardMap.put(Position.of(5, 6), NoPiece.getInstance());
        boardMap.put(Position.of(5, 7), NoPiece.getInstance());
        boardMap.put(Position.of(5, 8), NoPiece.getInstance());

        boardMap.put(Position.of(6, 1), NoPiece.getInstance());
        boardMap.put(Position.of(6, 2), NoPiece.getInstance());
        boardMap.put(Position.of(6, 3), NoPiece.getInstance());
        boardMap.put(Position.of(6, 4), NoPiece.getInstance());
        boardMap.put(Position.of(6, 5), NoPiece.getInstance());
        boardMap.put(Position.of(6, 6), NoPiece.getInstance());
        boardMap.put(Position.of(6, 7), NoPiece.getInstance());
        boardMap.put(Position.of(6, 8), NoPiece.getInstance());

        boardMap.put(Position.of(7, 1), NoPiece.getInstance());
        boardMap.put(Position.of(7, 2), NoPiece.getInstance());
        boardMap.put(Position.of(7, 3), NoPiece.getInstance());
        boardMap.put(Position.of(7, 4), NoPiece.getInstance());
        boardMap.put(Position.of(7, 5), NoPiece.getInstance());
        boardMap.put(Position.of(7, 6), NoPiece.getInstance());
        boardMap.put(Position.of(7, 7), NoPiece.getInstance());
        boardMap.put(Position.of(7, 8), NoPiece.getInstance());

        boardMap.put(Position.of(8, 1), NoPiece.getInstance());
        boardMap.put(Position.of(8, 2), NoPiece.getInstance());
        boardMap.put(Position.of(8, 3), NoPiece.getInstance());
        boardMap.put(Position.of(8, 4), NoPiece.getInstance());
        boardMap.put(Position.of(8, 5), NoPiece.getInstance());
        boardMap.put(Position.of(8, 6), NoPiece.getInstance());
        boardMap.put(Position.of(8, 7), NoPiece.getInstance());
        boardMap.put(Position.of(8, 8), NoPiece.getInstance());

        return boardMap;
    }
}
