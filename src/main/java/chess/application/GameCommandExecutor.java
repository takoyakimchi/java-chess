package chess.application;

import chess.domain.game.Game;

@FunctionalInterface
public interface GameCommandExecutor {

    Game execute();
}
