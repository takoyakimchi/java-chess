package chess.domain.game.state;

import chess.domain.piece.Color;

public class BlackTurnState extends TurnState {

    @Override
    public GameState move() {
        return new WhiteTurnState();
    }

    @Override
    public Color currentTurn() {
        return Color.BLACK;
    }
}
