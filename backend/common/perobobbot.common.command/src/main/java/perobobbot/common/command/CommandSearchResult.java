package perobobbot.common.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandSearchResult {

    /**
     * The command
     */
    private final @NonNull Command command;

    /**
     * The full name of the command (with main and sub command names)
     */
    private final String fullCommandName;

    /**
     * The rest of the message (after the full command name)
     */
    private final String parameters;



}
