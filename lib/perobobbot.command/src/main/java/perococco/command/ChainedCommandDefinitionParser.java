package perococco.command;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandDefinitionParsingFailure;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ChainedCommandDefinitionParser implements CommandDefinitionParser {

    private final @NonNull ImmutableList<CommandDefinitionParser> parsers;

    @Override
    public @NonNull CommandDefinitionParsingResult parse(@NonNull String commandDefinition) {
        final List<CommandDefinitionParsingFailure> suppressed = new ArrayList<>();
        for (CommandDefinitionParser parser : parsers) {
            try {
                return parser.parse(commandDefinition);
            } catch (CommandDefinitionParsingFailure failure) {
                suppressed.add(failure);
            }
        }
        final var failure = new CommandDefinitionParsingFailure("No parser could parse the definition",commandDefinition);
        suppressed.forEach(failure::addSuppressed);
        throw failure;
    }
}
