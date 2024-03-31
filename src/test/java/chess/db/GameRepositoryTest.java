package chess.db;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.board.Board;
import chess.domain.board.DeserializingBoardGenerator;
import chess.domain.game.Game;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
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
    }

    @Test
    @DisplayName("보드 및 턴 정보를 저장하고 그대로 읽을 수 있다.")
    void saveAndLoadGame() {
        /*
        ......PK 8
        ........ 7
        ........ 6
        ........ 5
        ........ 4
        ........ 3
        ........ 2
        pk...... 1
        abcdefgh
        */
        Board board = Board.generatedBy(new DeserializingBoardGenerator(
            "pk............................................................PK"));
        Game game = Game.from(board);
        game = game.started();

        repository.saveGame(game.getBoard(), WHITE);
        Game loadGame = repository.loadGame();
        Board loadBoard = loadGame.getBoard();

        assertAll(
            () -> assertThat(loadBoard.findPieceAt(Position.of(1, 1))).isEqualTo(Pawn.withColor(WHITE)),
            () -> assertThat(loadBoard.findPieceAt(Position.of(2, 1))).isEqualTo(King.withColor(WHITE)),
            () -> assertThat(loadBoard.findPieceAt(Position.of(7, 8))).isEqualTo(Pawn.withColor(BLACK)),
            () -> assertThat(loadBoard.findPieceAt(Position.of(8, 8))).isEqualTo(King.withColor(BLACK)),
            () -> assertThatCode(() -> loadGame.moved(Position.of(2, 1), Position.of(2, 2)))
                .doesNotThrowAnyException(),
            () -> assertThatThrownBy(() -> loadGame.moved(Position.of(7, 8), Position.of(7, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("WHITE이 움직일 차례입니다.")
        );

    }
}
