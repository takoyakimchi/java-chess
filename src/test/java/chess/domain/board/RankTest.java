package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    @DisplayName("Rank 간의 차이 계산")
    void subtract() {
        Rank rank1 = Rank.valueOf(1);
        Rank rank2 = Rank.valueOf(2);

        assertThat(rank1.subtract(rank2)).isEqualTo(-1);
    }

    @Test
    @DisplayName("Rank가 2인지 여부 반환")
    void isRankTwo() {
        Rank rank = Rank.valueOf(2);
        assertThat(rank.isRankTwo()).isTrue();
    }
}