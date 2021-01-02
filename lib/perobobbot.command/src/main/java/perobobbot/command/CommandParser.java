package perobobbot.command;

import lombok.NonNull;
import perococco.command.PeroCommandParser;

import java.util.Optional;

public interface CommandParser {

    static @NonNull CommandParser create(@NonNull String commandDefinition) {
        return new PeroCommandParser(commandDefinition);
    }

    @NonNull String getFullCommandName();

    @NonNull Optional<CommandParsing> parse(@NonNull String command);
}
