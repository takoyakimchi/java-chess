package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeserializingBoardGeneratorTest {

    @Test
    @DisplayName("문자열을 받아 체스판을 올바르게 생성한다")
    void generate() {
        String boardText = "rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR";
        Board board = Board.generatedBy(new DeserializingBoardGenerator(boardText));

        assertThat(board).isEqualTo(Board.generatedBy(new InitialBoardGenerator()));
    }
}
