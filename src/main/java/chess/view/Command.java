package chess.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {

    private static final String DELIMITER = " ";

    private final CommandType type;
    private final List<String> arguments;

    private Command(CommandType type, List<String> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    public static Command from(String command) {
        List<String> inputs = Arrays.stream(command.split(DELIMITER)).toList();
        return new Command(CommandType.from(command), new ArrayList<>(inputs.subList(1, inputs.size())));
    }

    public CommandType type() {
        return type;
    }

    public String argumentOf(int index) {
        try {
            return arguments.get(index);
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("해당 index에 명령어 인자가 존재하지 않습니다.");
        }
    }
}
