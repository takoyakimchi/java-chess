package chess.domain.game.state;

import chess.domain.piece.Color;

public class WhiteTurnState extends TurnState {

    @Override
    public GameState move() {
        return new BlackTurnState();
    }

    @Override
    public Color currentTurn() {
        return Color.WHITE;
    }
}
