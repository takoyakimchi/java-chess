package chess.domain.piece.slidingpiece;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.EmptyBoardGenerator;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QueenTest {

    private Map<Position, Piece> BOARD_MAP = new HashMap<>();

    @BeforeEach
    void beforeEach() {
        BOARD_MAP = EmptyBoardGenerator.create();
    }

    @DisplayName("퀸 이동 가능")
    @ParameterizedTest(name = "(4,4)->({0},{1}) 가능")
    @CsvSource({"6,4", "2,4", "4,6", "4,2", "6,6", "2,2", "2,6", "6,2"})
    void canMove(int file, int rank) {
        Piece queen = Queen.withColor(WHITE);
        Piece captured = Knight.withColor(BLACK);

        Position source = Position.of(4, 4);
        Position target = Position.of(file, rank);

        BOARD_MAP.put(source, queen);
        BOARD_MAP.put(Position.of(6, 4), captured);

        assertThat(queen.canMove(source, target, BOARD_MAP)).isTrue();
    }

    @DisplayName("퀸 이동 불가")
    @ParameterizedTest(name = "{0}: (4,4)->({1},{2}) 불가")
    @CsvSource({"이동 규칙 위반,5,6", "같은 위치,4,4", "자신의 말이 위치,4,6", "이동 경로에 말이 존재,4,2"})
    void cannotMove(String description, int file, int rank) {
        Piece queen = Queen.withColor(WHITE);
        Piece ownPiece = Pawn.withColor(WHITE);
        Piece pieceOnRoute = Pawn.withColor(BLACK);

        Position source = Position.of(4, 4);
        Position target = Position.of(file, rank);

        BOARD_MAP.put(source, queen);
        BOARD_MAP.put(Position.of(4, 6), ownPiece);
        BOARD_MAP.put(Position.of(4, 3), pieceOnRoute);

        assertThat(queen.canMove(source, target, BOARD_MAP)).isFalse();
    }
}
