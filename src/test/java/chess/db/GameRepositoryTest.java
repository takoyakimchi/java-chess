package chess.db;

import static chess.domain.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.game.Game;
import chess.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private final Connection connection = new ChessDao().getConnection();
    private final GameRepository repository = new GameRepository(connection);

    @BeforeEach
    void beforeEach() throws SQLException {
        connection.setAutoCommit(false);
        repository.initialize();
    }

    @AfterEach
    void afterEach() throws SQLException {
        connection.rollback();
    }

    @Test
    @DisplayName("테이블이 없으면 생성하고 초기 체스판 상태를 저장한다.")
    void initialize() throws SQLException {
        PreparedStatement dropTable = connection.prepareStatement("DROP TABLE IF EXISTS board");
        dropTable.executeUpdate();

        repository.initialize();

        PreparedStatement select = connection.prepareStatement(
            "SELECT COUNT(*) FROM information_schema.TABLES WHERE table_name='board' LIMIT 1");
        ResultSet resultSet = select.executeQuery();
        resultSet.next();
        assertThat(resultSet.getInt(1)).isEqualTo(1);

        Game game = repository.loadGame();
        assertThat(game.getBoard()).isEqualTo(Board.generatedBy(new InitialBoardGenerator()));
    }

    @Test
    @DisplayName("보드 및 턴 정보를 저장하고 그대로 읽을 수 있다.")
    void saveGame_loadGame() {
        Board board = Board.generatedBy(new InitialBoardGenerator());
        Game game = Game.from(board);
        game = game.start();

        repository.saveGame(game.getBoard(), WHITE);
        Game loadGame = repository.loadGame();

        assertAll(
            () -> assertThat(loadGame.getBoard()).isEqualTo(board),
            () -> assertThatCode(() -> loadGame.move(Position.of(1, 2), Position.of(1, 4)))
                .doesNotThrowAnyException(),
            () -> assertThatThrownBy(() -> loadGame.move(Position.of(1, 7), Position.of(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("체스판을 문자열로 변환할 수 있다.")
    void serializeBoard() {
        String boardText = repository.serializeBoard(Board.generatedBy(new InitialBoardGenerator()));
        assertThat(boardText).isEqualTo("rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR");
    }

    @Test
    @DisplayName("문자열을 체스판으로 변환할 수 있다.")
    void deserializeBoard() {
        String boardText = "rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR";
        Board board = repository.deserializeBoard(boardText);

        assertThat(board).isEqualTo(Board.generatedBy(new InitialBoardGenerator()));
    }
}
