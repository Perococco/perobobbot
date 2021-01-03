package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.Optional;
import java.util.UUID;

/**
 * Information about the context the execution
 * is performed in
 */
@RequiredArgsConstructor
public class ExecutionContext {

    @Getter
    @Delegate
    private final @NonNull MessageContext messageContext;

    /**
     * @return the name of the command
     */
    @Getter
    private final @NonNull String commandName;

    /**
     * @return the rest of the content after the command name
     */
    @Getter
    private final @NonNull String parameters;

    @NonNull
    public Optional<ExecutionContext> withSubCommands() {
        return splitCommandParameters(messageContext, parameters);
    }


    @NonNull
    public static Optional<ExecutionContext> createFrom(char prefix, @NonNull MessageContext messageContext) {
        if (!messageContext.doesContentStartWith(prefix)) {
            return Optional.empty();
        }
        return splitCommandParameters(messageContext, messageContext.getContent().substring(1));
    }


    @NonNull
    private static Optional<ExecutionContext> splitCommandParameters(@NonNull MessageContext messageContext, String source) {
        final String[] content = source.split(" ",2);

        final String command = content.length>0?content[0]:"";
        final String parameters = content.length>1?content[1]:"";

        if (command.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ExecutionContext(messageContext, command, parameters));
    }

    public @NonNull UUID getBotId() {
        return getChatConnectionInfo().getBotId();
    }
}
