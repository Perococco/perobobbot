package perococco.perobobbot.access.core;

import lombok.NonNull;
import perobobbot.access.core.AccessInfoExtractor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.User;

import java.time.Instant;

public class ProviderFromContext implements AccessInfoExtractor<ExecutionContext> {

    @Override
    public @NonNull User getExecutor(@NonNull ExecutionContext parameter) {
        return parameter.getMessageOwner();
    }

    @Override
    public @NonNull Instant getExecutionTime(@NonNull ExecutionContext parameter) {
        return parameter.getReceptionTime();
    }
}
