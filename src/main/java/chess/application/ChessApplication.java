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
        OUTPUT_VIEW.printStartMessage(GAME_REPOSITORY.findGameIds());
        int gameId = selectGame();
        Game game = GAME_REPOSITORY.findGameById(gameId);
        OUTPUT_VIEW.printMessageWhenEnteredRoom(gameId);
        OUTPUT_VIEW.printBoard(game.getBoard());
        while (game.isRunning()) {
            game = play(game, gameId);
        }
        OUTPUT_VIEW.printEndMessage();
        OUTPUT_VIEW.printStatus(game.decideWinStatus());
    }

    private static int selectGame() {
        try {
            Command command = INPUT_VIEW.readCommand();
            return executeLobbyCommand(command);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException exception) {
            OUTPUT_VIEW.printErrorMessage(exception.getMessage());
            return selectGame();
        }
    }

    private static int executeLobbyCommand(Command command) {
        if (command.type() == CommandType.MAKE) {
            return GAME_REPOSITORY.makeGameThenFindId(Board.generatedBy(new InitialBoardGenerator()), Color.WHITE);
        }
        if (command.type() == CommandType.ENTER) {
            return Integer.parseInt(command.argumentOf(0));
        }
        throw new IllegalArgumentException("게임 로비에서 사용할 수 없는 명령어입니다.");
    }

    private static Game play(Game game, int gameId) {
        try {
            Command command = INPUT_VIEW.readCommand();
            return executeGameCommand(game, gameId, command);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException exception) {
            OUTPUT_VIEW.printErrorMessage(exception.getMessage());
            return play(game, gameId);
        }
    }

    private static Game executeGameCommand(Game game, int gameId, Command command) {
        Map<CommandType, GameCommandExecutor> commandExecutionMap = Map.of(
            CommandType.START, () -> start(game),
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

    private static Game start(Game game) {
        game.start();
        GAME_REPOSITORY.initialize();
        game = GAME_REPOSITORY.loadGame();
        OUTPUT_VIEW.printBoard(game.getBoard());
        return game;
    }

    private static Game move(Game game, int gameId, Command command) {
        Position source = INPUT_VIEW.resolvePosition(command.argumentOf(0));
        Position target = INPUT_VIEW.resolvePosition(command.argumentOf(1));
        game = game.moved(source, target);
        if (game.isRunning()) {
            GAME_REPOSITORY.saveGame(gameId, game.getBoard(), game.currentTurn());
        }
        if (game.isEnd()) {
            GAME_REPOSITORY.deleteGame(gameId);
//            GAME_REPOSITORY.saveGame(gameId, Board.generatedBy(new InitialBoardGenerator()), Color.WHITE);
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
