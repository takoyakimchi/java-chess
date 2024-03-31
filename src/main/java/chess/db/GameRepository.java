package chess.db;

import static chess.domain.piece.Color.WHITE;

import chess.domain.board.Board;
import chess.domain.board.DeserializingBoardGenerator;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.game.Game;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRepository {

    private final Connection connection;

    public GameRepository(Connection connection) {
        this.connection = connection;
    }

    public void initialize() {
        createTableIfNotExists();
        if (countRecords() == 0) {
            addInitialRecord();
        }
    }

    private void createTableIfNotExists() {
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

    private int countRecords() {
        String query = "SELECT COUNT(*) FROM board";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 기록 존재 여부를 판단할 수 없습니다.");
        }
    }

    private void addInitialRecord() {
        String query = "INSERT INTO board (board_text, turn_text) VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, serializeBoard(Board.generatedBy(new InitialBoardGenerator())));
            statement.setString(2, serializeColor(WHITE));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("초기 게임 기록 저장에 실패하였습니다.");
        }
    }

    public void saveGame(Board board, Color currentTurn) {
        String query = "UPDATE board SET board_text=?, turn_text=? ORDER BY game_id DESC LIMIT 1";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, serializeBoard(board));
            statement.setString(2, serializeColor(currentTurn));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 기록 저장에 실패하였습니다.");
        }
    }

    public Game loadGame() {
        String query = "SELECT * FROM board ORDER BY game_id DESC LIMIT 1";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String boardText = resultSet.getString("board_text");
                String turnText = resultSet.getString("turn_text");
                return Game.withTurn(deserializeBoard(boardText), deserializeColor(turnText));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("저장된 게임을 불러올 수 없습니다.");
        }
        return Game.from(Board.generatedBy(new InitialBoardGenerator())).started();
    }

    private String serializeBoard(Board board) {
        StringBuilder builder = new StringBuilder();
        for (int rank = 1; rank <= 8; rank++) {
            for (int file = 1; file <= 8; file++) {
                Piece piece = board.findPieceAt(Position.of(file, rank));
                builder.append(piece.text());
            }
        }
        return builder.toString();
    }

    private Board deserializeBoard(String boardText) {
        return Board.generatedBy(new DeserializingBoardGenerator(boardText));
    }

    private String serializeColor(Color color) {
        return color.name();
    }

    private Color deserializeColor(String colorText) {
        return Color.valueOf(colorText);
    }
}
