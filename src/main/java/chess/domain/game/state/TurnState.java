package chess.domain.game.state;

public abstract class TurnState implements GameState {

    @Override
    public void start() {
        throw new UnsupportedOperationException("게임이 이미 시작되었습니다.");
    }

    @Override
    public GameState end() {
        return new EndState();
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
