package perobobbot.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Nil;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.fp.TryResult;

/**
 * A command that launch an execution
 */
@RequiredArgsConstructor
public class Command {

    @Getter
    private @NonNull String extensionName;

    private @NonNull CommandParsing parsing;
    @Getter
    private @NonNull AccessRule defaultAccessRule;

    private @NonNull CommandAction execution;

    public @NonNull String getCommandFullName() {
        return parsing.getFullName();
    }

    public void execute(@NonNull ExecutionContext context) {
        execution.execute(parsing, context);
    }

    public @NonNull TryResult<Throwable,Nil> tryToExecute(@NonNull ExecutionContext context) {
        return Consumer1.toConsumer1(this::execute).toTry().fSafe(context);
    }

}
