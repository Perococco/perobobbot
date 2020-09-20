package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.program.core.ExecutionContext;

public class ConsumedExecutionContext extends ProxyExecutionContext {

    public ConsumedExecutionContext(@NonNull ExecutionContext delegate) {
        super(delegate);
    }

    @Override
    public boolean isConsumed() {
        return true;
    }

    @Override
    public @NonNull ExecutionContext consumed() {
        return this;
    }
}
