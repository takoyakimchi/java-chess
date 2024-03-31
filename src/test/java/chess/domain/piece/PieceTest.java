package chess.domain.piece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.slidingpiece.Rook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    @DisplayName("WHITE의 말인지 여부 확인")
    void isWhite() {
        Rook rook = Rook.withColor(WHITE);
        assertThat(rook.isWhite()).isTrue();
    }

    @Test
    @DisplayName("반대 색을 불러올 수 있다.")
    void hasOppositeColorFrom() {
        Piece whitePawn = Pawn.withColor(WHITE);
        Piece blackPawn = Pawn.withColor(BLACK);
        assertAll(
            () -> assertThat(whitePawn.hasOppositeColorFrom(blackPawn)).isTrue(),
            () -> assertThat(blackPawn.hasOppositeColorFrom(whitePawn)).isTrue()
        );
    }

    @Test
    @DisplayName("King 여부 정상 반환")
    void isKing() {
        Piece whiteKing = King.withColor(WHITE);
        Piece blackKing = King.withColor(BLACK);
        Piece notKing = Pawn.withColor(WHITE);

        assertAll(
            () -> assertThat(whiteKing.isKing()).isTrue(),
            () -> assertThat(blackKing.isKing()).isTrue(),
            () -> assertThat(notKing.isKing()).isFalse()
        );
    }
}
