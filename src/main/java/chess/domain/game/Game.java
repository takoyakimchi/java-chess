package chess.domain.game;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import chess.domain.board.Board;
import chess.domain.game.state.GameState;
import chess.domain.game.state.ReadyState;
import chess.domain.position.Position;
import chess.view.WinStatusDto;

public class Game {

    private final Board board;
    private final GameState gameState;

    private Game(Board board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    public static Game from(Board board) {
        return new Game(board, new ReadyState());
    }

    public Game start() {
        return new Game(board, gameState.start());
    }

    public Game move(Position source, Position target) {
        GameState nextState = gameState.move();
        board.move(source, target, gameState.currentTurn());
        return new Game(board, nextState);
    }

    public Game end() {
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

    public Board getBoard() {
        return board;
    }
}
