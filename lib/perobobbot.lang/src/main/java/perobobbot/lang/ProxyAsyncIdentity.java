package perobobbot.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.*;
import perococco.perobobbot.common.lang.PerococcoAsyncIdentity;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class ProxyAsyncIdentity<S> implements AsyncIdentity<S> {

    @NonNull
    private final AsyncIdentity<S> delegate;

    public ProxyAsyncIdentity(@NonNull S initialValue) {
        this.delegate = AsyncIdentity.create(initialValue);
    }

    @Override
    public @NonNull <T> CompletionStage<T> operate(@NonNull Operator<S, T> operator) {
        return delegate.operate(operator);
    }

    @Override
    public @NonNull CompletionStage<S> getRootState() {
        return delegate.getRootState();
    }

    @Override
    @NonNull
    public Subscription addListener(@NonNull IdentityListener<S> listener) {
        return delegate.addListener(listener);
    }

    @Override
    public void addWeakListener(@NonNull IdentityListener<S> listener) {
        delegate.addWeakListener(listener);
    }
}
