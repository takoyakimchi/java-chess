package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    @DisplayName("Rank 간의 차이 계산")
    void subtract() {
        Rank rank1 = new Rank(1);
        Rank rank2 = new Rank(2);

        assertThat(rank1.subtract(rank2)).isEqualTo(-1);
    }
}