package chess.domain.game.state;

import chess.domain.piece.Color;

public class EndState implements GameState {

    @Override
    public GameState start() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public GameState move() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public GameState end() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public Color currentTurn() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
