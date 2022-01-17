package perococco.command;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public interface CommandDefinitionParser {

    @NonNull CommandDefinitionParsingResult parse(@NonNull String commandDefinition);


    static @NonNull CommandDefinitionParser chain(CommandDefinitionParser... parsers) {
        final var parserList = ImmutableList.copyOf(parsers);
        return new ChainedCommandDefinitionParser(parserList);
    }

}
