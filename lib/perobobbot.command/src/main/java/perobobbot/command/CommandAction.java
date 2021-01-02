package perobobbot.command;

import lombok.NonNull;
import perobobbot.lang.ExecutionContext;

public interface CommandAction {
    /**
     * Associated with a command parser and it is executed when the parsing is successful.
     * @param parsing result of the parsing
     * @param context the context in which the command is executed (like the user, the time of execution, the source
     *                of the execution)
     */
    void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context);
}
