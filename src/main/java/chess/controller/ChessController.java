package chess.controller;

import chess.application.GameCommandExecutor;
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

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public ChessController(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void run() {
        gameRepository.createTableIfNotExists();
        outputView.printStartMessage();
        outputView.printGameIds(gameRepository.findGameIds());
        int gameId = selectGame();
        Game game = findGame(gameId);
        outputView.printMessageWhenEnteredRoom(gameId);
        outputView.printBoard(game.getBoard());
        while (game.isRunning()) {
            game = play(game, gameId);
        }
        outputView.printEndMessage();
        outputView.printStatus(game.decideWinStatus());
    }

    private Game findGame(int gameId) {
        try {
            return gameRepository.findGameById(gameId);
        } catch (IllegalStateException exception) {
            outputView.printErrorMessage(exception.getMessage());
            return gameRepository.findGameById(selectGame());
        }
    }

    private int selectGame() {
        try {
            Command command = inputView.readCommand();
            return executeLobbyCommand(command);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException exception) {
            outputView.printErrorMessage(exception.getMessage());
            return selectGame();
        }
    }

    private int executeLobbyCommand(Command command) {
        if (command.type() == CommandType.MAKE) {
            return gameRepository.makeGameThenFindId(Board.generatedBy(new InitialBoardGenerator()), Color.WHITE);
        }
        if (command.type() == CommandType.ENTER) {
            return Integer.parseInt(command.argumentOf(0));
        }
        throw new IllegalArgumentException("게임 로비에서 사용할 수 없는 명령어입니다.");
    }

    private Game play(Game game, int gameId) {
        try {
            Command command = inputView.readCommand();
            return executeGameCommand(game, gameId, command);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException exception) {
            outputView.printErrorMessage(exception.getMessage());
            return play(game, gameId);
        }
    }

    private Game executeGameCommand(Game game, int gameId, Command command) {
        Map<CommandType, GameCommandExecutor> commandExecutionMap = Map.of(
            CommandType.MOVE, () -> move(game, gameId, command),
            CommandType.STATUS, () -> showStatus(game),
            CommandType.END, () -> end(game)
        );
        try {
            return commandExecutionMap.get(command.type()).execute();
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("게임 진행중에 입력할 수 없는 명령어입니다.");
        }
    }

    private Game move(Game game, int gameId, Command command) {
        Position source = inputView.resolvePosition(command.argumentOf(0));
        Position target = inputView.resolvePosition(command.argumentOf(1));
        game = game.moved(source, target);
        if (game.isRunning()) {
            gameRepository.saveGame(gameId, game.getBoard(), game.currentTurn());
        }
        if (game.isEnd()) {
            gameRepository.deleteGame(gameId);
        }
        outputView.printBoard(game.getBoard());
        return game;
    }

    private Game showStatus(Game game) {
        outputView.printStatus(game.decideWinStatus());
        return game;
    }

    private Game end(Game game) {
        return game.ended();
    }
}
