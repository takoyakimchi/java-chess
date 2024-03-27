package chess.view;

import chess.domain.game.WinStatus;

public record WinStatusDto(double whiteScore, double blackScore, WinStatus winStatus) {

}
