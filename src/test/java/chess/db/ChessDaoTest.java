package chess.db;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessDaoTest {

    private final ChessDao chessDao = new ChessDao();

    @Test
    @DisplayName("DB 연결 성공")
    public void connection() throws SQLException {
        try (Connection connection = chessDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    @DisplayName("체스판을 문자열로 변환할 수 있다.")
    void serializeBoard() {
        String boardText = chessDao.serializeBoard(Board.generatedBy(new InitialBoardGenerator()));
        assertThat(boardText).isEqualTo("rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR");
    }

    @Test
    @DisplayName("문자열을 체스판으로 변환할 수 있다.")
    void deserializeBoard() {
        String boardText = "rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR";
        Board board = chessDao.deserializeBoard(boardText);

        assertThat(board).isEqualTo(Board.generatedBy(new InitialBoardGenerator()));
    }

    //    @Test
//    @DisplayName("보드를 저장하고 읽을 수 있다.")
//    void saveBoard() {
//        Board board = Board.generatedBy(new InitialBoardGenerator());
//
//        chessDao.saveBoard(board);
//        Board readBoard = chessDao.readBoard();
//
//        assertThat(readBoard).isEqualTo(board);
//    }
}
