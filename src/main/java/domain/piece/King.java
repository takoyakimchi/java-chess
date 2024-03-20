package domain.piece;

import domain.board.Position;

public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Position sourcePosition, Position targetPosition) {
        if (sourcePosition.equals(targetPosition)) {
            return false;
        }
        int rankDifference = sourcePosition.calculateRankDifference(targetPosition);
        int fileDifference = sourcePosition.calculateFileDifference(targetPosition);
        return Math.abs(rankDifference) <= 1 && Math.abs(fileDifference) <= 1;
    }
}
