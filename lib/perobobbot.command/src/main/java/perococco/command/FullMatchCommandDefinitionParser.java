package perococco.command;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.command.CommandDefinitionParsingFailure;

import java.util.regex.Pattern;

public enum FullMatchCommandDefinitionParser implements CommandDefinitionParser {
    INSTANCE,
    ;

    public static @NonNull FullMatchCommandDefinitionParser create() {
        return INSTANCE;
    }

    public static final Pattern FULL_MATCH_COMMAND = Pattern.compile("^(?<command>[a-zA-Z0-9_]+)\\*$");

    @Override
    public @NonNull CommandDefinitionParsingResult parse(@NonNull String commandDefinition) {
        final var match = FULL_MATCH_COMMAND.matcher(commandDefinition);
        if (!match.matches()) {
            throw new CommandDefinitionParsingFailure("No a full match command",commandDefinition);
        }

        final var command = match.group("command");
        final var regex =  "^%s( .*)?$".formatted(command);
        return new CommandDefinitionParsingResult(regex, command, command, ImmutableSet.of());
    }
}
