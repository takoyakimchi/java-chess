package chess.domain.game;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import chess.domain.board.Board;
import chess.domain.game.state.BlackTurnState;
import chess.domain.game.state.GameState;
import chess.domain.game.state.ReadyState;
import chess.domain.game.state.WhiteTurnState;
import chess.domain.piece.Color;
import chess.domain.position.Position;
import chess.view.WinStatusDto;

public class Game {

    private static final int INITIAL_KING_COUNT = 2;

    private final Board board;
    private final GameState gameState;

    private Game(Board board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    public static Game from(Board board) {
        return new Game(board, new ReadyState());
    }

    public static Game withTurn(Board board, Color currentTurn) {
        if (currentTurn == WHITE) {
            return new Game(board, new WhiteTurnState());
        }
        return new Game(board, new BlackTurnState());
    }

    public Game started() {
        return new Game(board, gameState.start());
    }

    public Game moved(Position source, Position target) {
        board.move(source, target, gameState.currentTurn());
        GameState nextState = gameState.move();
        if (isKingCaptured()) {
            nextState = gameState.end();
        }
        return new Game(board, nextState);
    }

    private boolean isKingCaptured() {
        return board.kingCount() < INITIAL_KING_COUNT;
    }

    public Game ended() {
        return new Game(board, gameState.end());
    }

    public WinStatusDto decideWinStatus() {
        double whiteScore = board.totalScore(WHITE);
        double blackScore = board.totalScore(BLACK);
        return new WinStatusDto(whiteScore, blackScore, WinStatus.decideWinStatus(whiteScore, blackScore));
    }

    public boolean isRunning() {
        return !gameState.isEnd();
    }

    public boolean isEnd() {
        return gameState.isEnd();
    }

    public Color currentTurn() {
        return gameState.currentTurn();
    }

    public Board getBoard() {
        return board;
    }
}
