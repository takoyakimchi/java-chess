package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    @DisplayName("Score끼리 더할 수 있다.")
    void add() {
        Score score1 = Score.valueOf(1.0);
        Score score2 = Score.valueOf(2.5);
        assertThat(score1.add(score2)).isEqualTo(Score.valueOf(3.5));
    }
}
