package chess.view;

import static chess.domain.game.WinStatus.BLACK_WIN;
import static chess.domain.game.WinStatus.WHITE_WIN;

import chess.domain.board.Board;
import chess.domain.game.WinStatus;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.List;

public class OutputView {

    private static final String END_COMMAND = "end";
    private static final String MOVE_COMMAND = "move";
    private static final String MAKE_COMMAND = "make";
    private static final String ENTER_COMMAND = "enter";

    private final PieceTextMapper pieceTextMapper;

    public OutputView() {
        this.pieceTextMapper = PieceTextMapper.initialize();
    }

    public void printStartMessage() {
        System.out.println("> 체스 게임을 시작합니다.");
    }

    public void printGameIds(List<Integer> gameIds) {
        System.out.println();
        System.out.println("[방 목록]");
        if (gameIds.isEmpty()) {
            System.out.printf("방이 없습니다. %s 명령어를 입력하여 새로 만들어 주세요.%n", MAKE_COMMAND);
        }
        gameIds.forEach(id -> System.out.printf("- 방 번호: %d%n", id));
        System.out.println();
        System.out.printf("> 방 만들기 : %s (방 번호는 자동으로 부여됩니다.)%n", MAKE_COMMAND);
        System.out.printf("> 방 들어가기 : %s 방번호 - 예. %s 4%n%n", ENTER_COMMAND, ENTER_COMMAND);
    }

    public void printMessageWhenEnteredRoom(int roomNumber) {
        System.out.printf("%d번 방에 접속하였습니다.%n%n", roomNumber);
        System.out.printf("> 게임 종료 : %s%n", END_COMMAND);
        System.out.printf("> 게임 이동 : %s source위치 target위치 - 예. %s b2 b3%n%n", MOVE_COMMAND, MOVE_COMMAND);
    }

    public void printBoard(Board board) {
        for (int rank = 8; rank >= 1; rank--) {
            printOneRank(board, rank);
        }
        System.out.println();
        System.out.println("abcdefgh");
        System.out.println();
    }

    private void printOneRank(Board board, int rank) {
        for (int file = 1; file <= 8; file++) {
            Piece piece = board.findPieceAt(Position.of(file, rank));
            System.out.print(pieceTextMapper.textOf(piece));
        }
        System.out.printf("  %d%n", rank);
    }

    public void printStatus(WinStatusDto winStatusDto) {
        System.out.printf("현재 게임 결과: %s%n", winStatusMessage(winStatusDto.winStatus()));
        System.out.printf("WHITE의 점수: %.1f, BLACK의 점수: %.1f%n%n", winStatusDto.whiteScore(), winStatusDto.blackScore());
    }

    private String winStatusMessage(WinStatus winStatus) {
        if (winStatus == WHITE_WIN) {
            return "WHITE 승";
        }
        if (winStatus == BLACK_WIN) {
            return "BLACK 승";
        }
        return "무승부";
    }

    public void printEndMessage() {
        System.out.println("게임이 종료되었습니다.");
    }

    public void printErrorMessage(String message) {
        System.err.printf("[ERROR] %s%n", message);
    }
}
