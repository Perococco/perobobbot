package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.User;
import perobobbot.common.lang.fp.Function1;
import perobobbot.program.core.ExecutionContext;

import java.time.Instant;

@RequiredArgsConstructor
public class ProxyExecutionContext implements ExecutionContext {

    @NonNull
    private final ExecutionContext delegate;

    @Override
    public boolean executingUserIsMe() {
        return delegate.executingUserIsMe();
    }

    @Override
    public @NonNull User getExecutingUser() {
        return delegate.getExecutingUser();
    }

    @Override
    public @NonNull Instant getReceptionTime() {
        return delegate.getReceptionTime();
    }

    @Override
    @NonNull
    public String getRawPayload() {
        return delegate.getRawPayload();
    }

    @Override
    @NonNull
    public String getMessage() {
        return delegate.getMessage();
    }

    @Override
    public boolean isConsumed() {
        return delegate.isConsumed();
    }

    @Override
    @NonNull
    public ExecutionContext consumed() {
        return delegate.consumed();
    }

    @Override
    public void print(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        delegate.print(messageBuilder);
    }

    @Override
    public void print(@NonNull String message) {
        delegate.print(message);
    }
}
