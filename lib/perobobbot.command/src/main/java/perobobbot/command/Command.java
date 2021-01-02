package perobobbot.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;
import perobobbot.lang.ExecutionContext;

/**
 * A command that launch an execution
 */
@RequiredArgsConstructor
public class Command {

    private @NonNull CommandParsing parsing;
    @Getter
    private @NonNull AccessRule defaultAccessRule;

    private @NonNull CommandAction execution;

    public @NonNull String getFullCommand() {
        return parsing.getFullName();
    }

    public void execute(@NonNull ExecutionContext context) {
        execution.execute(parsing, context);
    }

}
