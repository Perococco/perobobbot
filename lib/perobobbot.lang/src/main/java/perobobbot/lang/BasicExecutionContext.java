package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Information about the context the execution
 * is performed in
 */
@RequiredArgsConstructor
public class BasicExecutionContext implements ExecutionContext {

    @Getter
    private final @NonNull MessageContext messageContext;

    /**
     * @return the full command without the prefix
     */
    @Getter
    private final @NonNull String command;

}
