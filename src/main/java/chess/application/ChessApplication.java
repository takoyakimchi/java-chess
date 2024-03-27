package chess.application;

import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.game.Game;
import chess.domain.position.Position;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        outputView.printStartMessage();
        Command command = tryReadCommand();
        if (command.isStart()) {
            Board board = Board.generatedBy(new InitialBoardGenerator());
            Game game = Game.from(board);
            outputView.printBoard(board);
            start(game);
        }
    }

    private static void start(Game game) {
        Command command = tryReadCommand();
        if (command.isEnd()) {
            return;
        }
        if (command.isStatus()) {
            outputView.printStatus(game.decideWinStatus());
        }
        if (command.isMove()) {
            game = tryMove(game, command);
        }
        start(game);
    }

    private static Command tryReadCommand() {
        try {
            return inputView.readCommand();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return tryReadCommand();
        }
    }

    private static Game tryMove(Game game, Command command) {
        try {
            Position source = inputView.resolvePosition(command.sourcePosition());
            Position target = inputView.resolvePosition(command.targetPosition());
            game = game.move(source, target);
            outputView.printBoard(game.getBoard());
        } catch (IllegalArgumentException e) {
            outputView.printMessage(e.getMessage());
        }
        return game;
    }
}
