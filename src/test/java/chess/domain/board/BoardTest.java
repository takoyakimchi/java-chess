package chess.domain.board;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.piece.Color;
import chess.domain.piece.EmptyBoardGenerator;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.slidingpiece.Bishop;
import chess.domain.piece.slidingpiece.Queen;
import chess.domain.piece.slidingpiece.Rook;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Map<Position, Piece> BOARD_MAP = new HashMap<>();

    @BeforeEach
    void beforeEach() {
        BOARD_MAP = EmptyBoardGenerator.create();
    }

    @Test
    @DisplayName("실패: 출발점에 말이 없으면 이동 불가")
    void move_NoPieceAtSourcePosition() {
        Board board = Board.generatedBy(new InitialBoardGenerator());
        Position sourcePosition = Position.of(4, 4);
        Position targetPosition = Position.of(4, 5);

        assertThatThrownBy(() -> board.move(sourcePosition, targetPosition, Color.WHITE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("출발점에 말이 없습니다.");
    }

    @Test
    @DisplayName("실패: 말의 규칙에 맞지 않는 위치로 이동")
    void move_IllegalMove() {
        Position sourcePosition = Position.of(2, 1);
        Position targetPosition = Position.of(3, 4);
        Board board = Board.generatedBy(new InitialBoardGenerator());

        assertThatThrownBy(() -> board.move(sourcePosition, targetPosition, Color.WHITE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("말의 규칙에 맞지 않는 이동입니다.");
    }

    /**
     * Queen(9) + Rook(5) + Bishop(3) + Knight(2.5) + King(0) + Pawn(1) == 20.5
     */
    @Test
    @DisplayName("퀸 + 룩 + 비숍 + 나이트 + 킹 + 폰 == 20.5점")
    void totalScore_General() {
        BOARD_MAP.put(Position.of(1, 1), Queen.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 2), Rook.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 3), Bishop.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 4), Knight.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 5), King.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 6), Pawn.withColor(WHITE));

        Board board = Board.generatedBy(() -> BOARD_MAP);

        assertThat(board.totalScore(WHITE)).isEqualTo(20.5);
    }

    @Test
    @DisplayName("킹이 잡히면 0점")
    void totalScore_Zero_NoKing() {
        BOARD_MAP.put(Position.of(1, 1), Queen.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 2), Rook.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 3), Bishop.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 4), Knight.withColor(WHITE));
        BOARD_MAP.put(Position.of(1, 5), Pawn.withColor(WHITE));

        Board board = Board.generatedBy(() -> BOARD_MAP);

        assertThat(board.totalScore(WHITE)).isEqualTo(0.0);
    }

    /**
     * ........ 8 ........ 7 ........ 6 ...p.... 5   0.5 ...p.k.. 4   0.5     0.0 ...p.... 3   0.5 .....pp. 2 1.0
     * 1.0 ........ 1 abcdefgh
     */
    @Test
    @DisplayName("같은 File에 폰 2개 + 킹 + 폰 + 폰 == 3점")
    void totalScore_TwoPawnsOnSameFile() {
        BOARD_MAP.put(Position.of(4, 3), Pawn.withColor(WHITE));
        BOARD_MAP.put(Position.of(4, 4), Pawn.withColor(WHITE));
        BOARD_MAP.put(Position.of(4, 5), Pawn.withColor(WHITE));
        BOARD_MAP.put(Position.of(6, 4), King.withColor(WHITE));
        BOARD_MAP.put(Position.of(6, 2), Pawn.withColor(WHITE));
        BOARD_MAP.put(Position.of(7, 2), Pawn.withColor(WHITE));

        Board board = Board.generatedBy(() -> BOARD_MAP);

        assertThat(board.totalScore(WHITE)).isEqualTo(3.5);
    }


    @Test
    @DisplayName("King의 개수를 셀 수 있다.")
    void kingCount() {
        BOARD_MAP.put(Position.of(4, 3), King.withColor(WHITE));
        BOARD_MAP.put(Position.of(4, 4), King.withColor(BLACK));

        Board board = Board.generatedBy(() -> BOARD_MAP);

        assertThat(board.kingCount()).isEqualTo(2);
    }
}
