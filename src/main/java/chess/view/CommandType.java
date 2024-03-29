package chess.view;

import java.util.Arrays;
import java.util.List;

public enum CommandType {

    START("start", 1),
    END("end", 1),
    MOVE("move", 3),
    STATUS("status", 1),
    ;

    public static final String DELIMITER = " ";
    private final String prefix;
    private final int argumentAmount;

    CommandType(String prefix, int argumentAmount) {
        this.prefix = prefix;
        this.argumentAmount = argumentAmount;
    }

    public static CommandType from(String input) {
        List<String> commands = Arrays.stream(input.split(DELIMITER)).toList();
        String prefix = commands.get(0);
        return Arrays.stream(values())
            .filter(value -> value.prefix.equals(prefix))
            .filter(value -> value.argumentAmount == commands.size())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 명령어 입력입니다."));
    }
}
