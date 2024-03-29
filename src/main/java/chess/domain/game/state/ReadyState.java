package chess.domain.game.state;

import chess.domain.piece.Color;

public class ReadyState implements GameState {

    @Override
    public GameState start() {
        return new WhiteTurnState();
    }

    @Override
    public GameState move() {
        throw new UnsupportedOperationException("게임을 시작해야 말을 움직일 수 있습니다.");
    }

    @Override
    public GameState end() {
        return new EndState();
    }

    @Override
    public Color currentTurn() {
        throw new UnsupportedOperationException("게임이 시작되지 않아 턴 정보를 알 수 없습니다.");
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
