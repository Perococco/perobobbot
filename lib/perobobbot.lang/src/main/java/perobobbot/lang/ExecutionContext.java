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
     * @return the full command without the prefix
     */
    @Getter
    private final @NonNull String command;

    @NonNull
    public static Optional<ExecutionContext> createFrom(char prefix, @NonNull MessageContext messageContext) {
        if (!messageContext.doesContentStartWith(prefix)) {
            return Optional.empty();
        }
        return splitCommandParameters(messageContext, messageContext.getContent().substring(1));
    }


    @NonNull
    private static Optional<ExecutionContext> splitCommandParameters(@NonNull MessageContext messageContext, String command) {
        if (command.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new ExecutionContext(messageContext, command));
    }

    public @NonNull UUID getBotId() {
        return getChatConnectionInfo().getBotId();
    }
}
