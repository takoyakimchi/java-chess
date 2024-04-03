package chess.application;

import chess.controller.ChessController;
import chess.db.GameRepository;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {

    public static void main(String[] args) {
        ChessController chessController = new ChessController(new InputView(), new OutputView(), new GameRepository());
        chessController.run();
    }
}
