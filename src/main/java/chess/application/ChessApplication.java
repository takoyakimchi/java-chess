package chess.application;

import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.game.Game;
import chess.domain.position.Position;
import chess.view.Command;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        outputView.printStartMessage();
        Board board = Board.generatedBy(new InitialBoardGenerator());
        Game game = Game.from(board);
        while (game.isRunning()) {
            game = play(game);
        }
    }

    private static Game play(Game game) {
        try {
            Command command = inputView.readCommand();
            return executeCommand(command, game);
        } catch (UnsupportedOperationException | IllegalArgumentException exception) {
            outputView.printMessage(exception.getMessage());
            return play(game);
        }
    }

    private static Game executeCommand(Command command, Game game) {
        if (command.type() == CommandType.START) {
            game = start(game);
        }
        if (command.type() == CommandType.MOVE) {
            game = move(game, command);
        }
        if (command.type() == CommandType.STATUS) {
            outputView.printStatus(game.decideWinStatus());
        }
        if (command.type() == CommandType.END) {
            game = end(game);
        }
        return game;
    }

    private static Game start(Game game) {
        game.start();
        outputView.printBoard(game.getBoard());
        return game.start();
    }

    private static Game move(Game game, Command command) {
        Position source = inputView.resolvePosition(command.argumentOf(0));
        Position target = inputView.resolvePosition(command.argumentOf(1));
        game = game.move(source, target);
        outputView.printBoard(game.getBoard());
        return game;
    }

    private static Game end(Game game) {
        return game.end();
    }
}
