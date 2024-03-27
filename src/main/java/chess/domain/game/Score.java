package chess.domain.game;

import java.util.Objects;

public class Score {

    private final double value;

    private Score(double value) {
        this.value = value;
    }

    public static Score valueOf(double value) {
        return new Score(value);
    }

    public Score add(Score other) {
        return new Score(this.value + other.value);
    }

    public Score multiply(long number) {
        return new Score(this.value * number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return Double.compare(value, score.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
