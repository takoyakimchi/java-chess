package chess.domain.game.state;

public abstract class TurnState implements GameState {

    @Override
    public GameState end() {
        return new EndState();
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
