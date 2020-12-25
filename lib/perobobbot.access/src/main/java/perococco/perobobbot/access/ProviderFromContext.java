package perococco.perobobbot.access;

import lombok.NonNull;
import perobobbot.access.AccessInfoExtractor;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.User;

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