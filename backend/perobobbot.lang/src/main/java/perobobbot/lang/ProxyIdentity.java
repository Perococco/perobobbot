package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

public class ProxyIdentity<S> implements Identity<S> {

    @NonNull
    private final Identity<S> delegate;

    public ProxyIdentity(@NonNull Identity<S> delegate) {
        this.delegate = delegate;
    }

    public ProxyIdentity(@NonNull S initialState) {
        this(Identity.create(initialState));
    }

    @Override
    @NonNull
    public <T> T mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter) {
        return delegate.mutateAndGet(mutation, getter);
    }

    @Override
    @NonNull
    public S mutate(@NonNull Mutation<S> mutation) {
        return delegate.mutate(mutation);
    }

    @Override
    @NonNull
    public S getState() {
        return delegate.getState();
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
