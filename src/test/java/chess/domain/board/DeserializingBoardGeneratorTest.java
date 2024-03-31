package chess.domain.board;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeserializingBoardGeneratorTest {

    @Test
    @DisplayName("문자열을 받아 체스판을 올바르게 생성한다")
    void generate() {
        /*
        ......PK 8
        ........ 7
        ........ 6
        ........ 5
        ........ 4
        ........ 3
        ........ 2
        pk...... 1
        abcdefgh
        */
        String boardText = "pk............................................................PK";
        Board board = Board.generatedBy(new DeserializingBoardGenerator(boardText));
        assertAll(
            () -> assertThat(board.findPieceAt(Position.of(1, 1))).isEqualTo(Pawn.withColor(WHITE)),
            () -> assertThat(board.findPieceAt(Position.of(2, 1))).isEqualTo(King.withColor(WHITE)),
            () -> assertThat(board.findPieceAt(Position.of(7, 8))).isEqualTo(Pawn.withColor(BLACK)),
            () -> assertThat(board.findPieceAt(Position.of(8, 8))).isEqualTo(King.withColor(BLACK))
        );
    }
}
