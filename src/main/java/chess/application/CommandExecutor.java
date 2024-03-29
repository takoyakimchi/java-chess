package chess.application;

import chess.domain.game.Game;

@FunctionalInterface
public interface CommandExecutor {

    Game execute();
}
