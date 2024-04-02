package chess.application;

import chess.db.GameRepository;
import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.game.Game;
import chess.domain.piece.Color;
import chess.domain.position.Position;
import chess.view.Command;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;

public class ChessApplication {

    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();
    private static final GameRepository GAME_REPOSITORY = new GameRepository();

    public static void main(String[] args) {
        OUTPUT_VIEW.printStartMessage();
        Board board = Board.generatedBy(new InitialBoardGenerator());
        Game game = Game.from(board);
        while (game.isRunning()) {
            game = play(game);
        }
        OUTPUT_VIEW.printEndMessage();
        OUTPUT_VIEW.printStatus(game.decideWinStatus());
    }

    private static Game play(Game game) {
        try {
            Command command = INPUT_VIEW.readCommand();
            return executeCommand(game, command);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException exception) {
            OUTPUT_VIEW.printErrorMessage(exception.getMessage());
            return play(game);
        }
    }

    private static Game executeCommand(Game game, Command command) {
        Map<CommandType, CommandExecutor> commandExecutionMap = commandExecutionMap(game, command);
        return commandExecutionMap.get(command.type()).execute();
    }

    private static Map<CommandType, CommandExecutor> commandExecutionMap(Game game, Command command) {
        return Map.of(
            CommandType.START, () -> start(game),
            CommandType.MOVE, () -> move(game, command),
            CommandType.STATUS, () -> showStatus(game),
            CommandType.END, () -> end(game)
        );
    }

    private static Game start(Game game) {
        game.start();
        GAME_REPOSITORY.initialize();
        game = GAME_REPOSITORY.loadGame();
        OUTPUT_VIEW.printBoard(game.getBoard());
        return game;
    }

    private static Game move(Game game, Command command) {
        Position source = INPUT_VIEW.resolvePosition(command.argumentOf(0));
        Position target = INPUT_VIEW.resolvePosition(command.argumentOf(1));
        game = game.moved(source, target);
        if (game.isRunning()) {
            GAME_REPOSITORY.saveGame(game.getBoard(), game.currentTurn());
        }
        if (game.isEnd()) {
            GAME_REPOSITORY.saveGame(Board.generatedBy(new InitialBoardGenerator()), Color.WHITE);
        }
        OUTPUT_VIEW.printBoard(game.getBoard());
        return game;
    }

    private static Game showStatus(Game game) {
        OUTPUT_VIEW.printStatus(game.decideWinStatus());
        return game;
    }

    private static Game end(Game game) {
        return game.ended();
    }
}
