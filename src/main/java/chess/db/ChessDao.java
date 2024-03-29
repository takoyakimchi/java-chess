package chess.db;

import chess.domain.board.Board;
import chess.domain.board.DeserializingBoardGenerator;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ChessDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }

    public void saveGame(Board board, Color currentTurn) {
        // end할 때마다 마지막 board를 저장하는 방식으로 한다.
    }

    public Board loadGame() {
        // start할 때 DB에 남아있는 board가 있으면 불러온다.
        return null;
    }

    public String serializeBoard(Board board) {
        StringBuilder builder = new StringBuilder();
        for (int rank = 1; rank <= 8; rank++) {
            for (int file = 1; file <= 8; file++) {
                Piece piece = board.findPieceAt(Position.of(file, rank));
                builder.append(piece.text());
            }
        }
        return builder.toString();
    }

    public Board deserializeBoard(String boardText) {
        return Board.generatedBy(new DeserializingBoardGenerator(boardText));
    }
}
