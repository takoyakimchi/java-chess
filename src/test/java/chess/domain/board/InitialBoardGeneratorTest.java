package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.slidingpiece.Bishop;
import chess.domain.piece.slidingpiece.Queen;
import chess.domain.piece.slidingpiece.Rook;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InitialBoardGeneratorTest {

    @Test
    @DisplayName("체스판을 초기화한다.")
    void initialize() {
        BoardGenerator boardGenerator = new InitialBoardGenerator();
        Board board = Board.generatedBy(boardGenerator);

        assertAll(
            () -> assertThat(board.findPieceAt(Position.of(1, 8))).isEqualTo(Rook.withColor(Color.BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(2, 8))).isEqualTo(Knight.withColor(Color.BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(3, 8))).isEqualTo(Bishop.withColor(Color.BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(4, 8))).isEqualTo(Queen.withColor(Color.BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(5, 8))).isEqualTo(King.withColor(Color.BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(6, 8))).isEqualTo(Bishop.withColor(Color.BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(7, 8))).isEqualTo(Knight.withColor(Color.BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(8, 8))).isEqualTo(Rook.withColor(Color.BLACK))
        );
    }
}
