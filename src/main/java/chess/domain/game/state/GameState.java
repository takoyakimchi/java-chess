package chess.domain.game.state;

import chess.domain.piece.Color;

public interface GameState {

    void start();

    GameState move();

    GameState end();

    Color currentTurn();

    boolean isEnd();
}
