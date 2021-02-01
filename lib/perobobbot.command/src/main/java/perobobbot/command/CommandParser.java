package perobobbot.command;

import lombok.NonNull;
import perococco.command.PeroCommandParser;

import java.util.Optional;

/**
 * A command parser is used to parse a command on the chat.
 *
 */
public interface CommandParser {

    /**
     * <p>Create a command parser from a definition. A command definition is of the form :</p>
     *
     * <code>command-name  {some-compulsory-param} [some-optional-param]</code>
     *
     * <p>For instance, a valid definition could be</p>
     *
     * <code>play {x},{y} [arg3]</code>
     *
     *
     * @param commandDefinition the definition of the command
     * @return a command parser created from the provided definition
     */
    static @NonNull CommandParser create(@NonNull String commandDefinition) {
        return new PeroCommandParser(commandDefinition);
    }

    @NonNull String getFullCommandName();

    @NonNull String getCommandDefinition();

    /**
     * Parse a command
     * @param command a command coming from the chat
     * @return an optional containing the parsing on success, an empty option otherwise
     */
    @NonNull Optional<CommandParsing> parse(@NonNull String command);

    boolean isInConflictWith(@NonNull CommandParser other);

}
