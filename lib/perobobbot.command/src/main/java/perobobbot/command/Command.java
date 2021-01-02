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
    private @NonNull CommandAction executor;

    public @NonNull String getFullCommand() {
        return parsing.getFullName();
    }

    public void execute(@NonNull ExecutionContext context) {
        executor.execute(parsing,context);
    }

}
