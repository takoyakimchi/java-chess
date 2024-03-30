package chess.application;

import chess.db.ChessDao;
import chess.db.GameRepository;
import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.game.Game;
import chess.domain.position.Position;
import chess.view.Command;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;

public class ChessApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final GameRepository gameRepository = GameRepository.from(new ChessDao());

    public static void main(String[] args) {
        outputView.printStartMessage();
        Board board = Board.generatedBy(new InitialBoardGenerator());
        Game game = Game.from(board);
        while (game.isRunning()) {
            game = play(game);
        }
        outputView.printEndMessage();
        outputView.printStatus(game.decideWinStatus());
    }

    private static Game play(Game game) {
        try {
            Command command = inputView.readCommand();
            return executeCommand(game, command);
        } catch (UnsupportedOperationException | IllegalArgumentException exception) {
            outputView.printMessage(exception.getMessage());
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
        gameRepository.createGameIfNotExists();
        game = gameRepository.loadGame();
        outputView.printBoard(game.getBoard());
        return game;
    }

    private static Game move(Game game, Command command) {
        Position source = inputView.resolvePosition(command.argumentOf(0));
        Position target = inputView.resolvePosition(command.argumentOf(1));
        game = game.move(source, target);
        gameRepository.saveGame(game.getBoard(), game.currentTurn());
        outputView.printBoard(game.getBoard());
        return game;
    }

    private static Game showStatus(Game game) {
        outputView.printStatus(game.decideWinStatus());
        return game;
    }

    private static Game end(Game game) {
        return game.end();
    }
}
