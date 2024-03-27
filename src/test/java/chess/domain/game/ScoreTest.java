package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.EmptyBoardGenerator;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    private Map<Position, Piece> BOARD_MAP = new HashMap<>();

    @BeforeEach
    void beforeEach() {
        BOARD_MAP = EmptyBoardGenerator.create();
    }

    @Test
    @DisplayName("Score끼리 더할 수 있다.")
    void add() {
        Score score1 = Score.valueOf(1.0);
        Score score2 = Score.valueOf(2.5);
        assertThat(score1.add(score2)).isEqualTo(Score.valueOf(3.5));
    }
}
