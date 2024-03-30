package chess.db;

import static chess.domain.piece.Color.BLACK;
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

    private GameRepository(Connection connection) {
        this.connection = connection;
    }

    public static GameRepository from(ChessDao chessDao) {
        return new GameRepository(chessDao.getConnection());
    }

    public void createGameIfNotExists() {
        String query = "CREATE TABLE IF NOT EXISTS board("
            + "game_id INT NOT NULL AUTO_INCREMENT,"
            + "board_text VARCHAR(255) NOT NULL,"
            + "turn_text VARCHAR(255) NOT NULL,"
            + "PRIMARY KEY (game_id));";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new UnsupportedOperationException("서버와의 연결이 끊겼습니다");
        }
    }

    public void saveGame(Board board, Color currentTurn) {
        String query = "INSERT INTO board (board_text, turn_text) VALUES(?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, serializeBoard(board));
            statement.setString(2, serializeColor(currentTurn));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new UnsupportedOperationException("서버와의 연결이 끊겼습니다");
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
            throw new UnsupportedOperationException("서버와의 연결이 끊겼습니다");
        }
        return Game.from(Board.generatedBy(new InitialBoardGenerator())).start();
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

    public String serializeColor(Color color) {
        if (color == WHITE) {
            return "WHITE";
        }
        return "BLACK";
    }

    public Color deserializeColor(String colorText) {
        if (colorText.equals("WHITE")) {
            return WHITE;
        }
        return BLACK;
    }
}
