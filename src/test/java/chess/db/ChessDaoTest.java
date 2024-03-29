package chess.db;

import static org.assertj.core.api.Assertions.assertThat;

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
}
