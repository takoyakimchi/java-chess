package chess.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.game.Game;
import chess.domain.piece.Color;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private final GameRepository repository = GameRepository.from(new ChessDao());

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

    @Test
    @DisplayName("보드를 저장하고 그대로 읽을 수 있다.")
    void saveBoard() {
        Board board = Board.generatedBy(new InitialBoardGenerator());
        Game game = Game.from(board);
        game = game.start();

        repository.saveGame(game.getBoard(), Color.WHITE);
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
    @DisplayName("테이블이 없으면 새로 생성할 수 있다.")
    void createGameIfNotExists() {

    }
}
