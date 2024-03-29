package chess.db;

import chess.domain.board.Board;
import chess.domain.piece.Color;
import chess.domain.piece.Pawn;
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

    public void saveBoard(Board board) {
        // end할 때마다 마지막 board를 저장하는 방식으로 한다.

        Pawn pawn = Pawn.withColor(Color.WHITE);
    }

    public Board readBoard() {
        // start할 때 DB에 남아있는 board가 있으면 불러온다.

        return null;
    }
}
