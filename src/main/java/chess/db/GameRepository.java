package chess.db;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import chess.domain.board.Board;
import chess.domain.game.Game;
import chess.domain.piece.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    private static final String URL = "localhost";
    private static final String PORT = "13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private final Connection connection;
    private final ObjectSerializer serializer;

    public GameRepository() {
        this.connection = getConnection();
        this.serializer = new ObjectSerializer();
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://" + URL + ":" + PORT + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("서버 연결이 끊겼습니다.");
        }
    }

    public void createTableIfNotExists() {
        String query = """
                CREATE TABLE IF NOT EXISTS board(
                game_id INT NOT NULL AUTO_INCREMENT,
                board_text VARCHAR(255) NOT NULL,
                turn_text VARCHAR(255) NOT NULL,
                PRIMARY KEY (game_id));
            """;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("초기 테이블 생성에 실패하였습니다.");
        }
    }

    public List<Integer> findGameIds() {
        String query = "SELECT game_id FROM board";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            return makeGameIdsFrom(resultSet);
        } catch (SQLException exception) {
            throw new IllegalStateException("방 목록을 찾을 수 없습니다.");
        }
    }

    private List<Integer> makeGameIdsFrom(ResultSet resultSet) throws SQLException {
        List<Integer> gameIds = new ArrayList<>();
        while (resultSet.next()) {
            gameIds.add(resultSet.getInt("game_id"));
        }
        return gameIds;
    }

    public Game findGameById(int roomNumber) {
        String query = "SELECT * FROM board WHERE game_id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String boardText = resultSet.getString("board_text");
            String turnText = resultSet.getString("turn_text");
            return Game.withTurn(serializer.deserializeBoard(boardText), serializer.deserializeColor(turnText));
        } catch (SQLException exception) {
            throw new IllegalStateException("방 번호를 올바르게 입력해 주세요.");
        }
    }

    public int makeGameThenFindId(Board board, Color currentTurn) {
        String query = "INSERT INTO board (board_text, turn_text) VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query, RETURN_GENERATED_KEYS);
            statement.setString(1, serializer.serializeBoard(board));
            statement.setString(2, serializer.serializeColor(currentTurn));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 생성에 실패하였습니다.");
        }
    }

    public void saveGame(int gameId, Board board, Color currentTurn) {
        String query = "UPDATE board SET board_text=?, turn_text=? WHERE game_id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, serializer.serializeBoard(board));
            statement.setString(2, serializer.serializeColor(currentTurn));
            statement.setInt(3, gameId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 기록 저장에 실패하였습니다.");
        }
    }

    public void deleteGame(int gameId) {
        String query = "DELETE FROM board WHERE game_id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("게임을 삭제할 수 없습니다.");
        }
    }
}
