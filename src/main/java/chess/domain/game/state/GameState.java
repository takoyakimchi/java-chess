package chess.domain.game.state;

import chess.domain.piece.Color;

public interface GameState {

    GameState start();

    GameState move();

    GameState end();

    Color currentTurn();

    boolean isEnd();
}
