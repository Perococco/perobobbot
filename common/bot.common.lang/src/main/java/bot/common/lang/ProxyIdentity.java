package bot.common.lang;

import bot.common.lang.fp.Function1;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class ProxyIdentity<S> implements Identity<S> {

    @NonNull
    private final Identity<S> delegate;

    @NonNull
    public static <S1> Identity<S1> create(@NonNull S1 initialValue) {
        return Identity.create(initialValue);
    }

    @Override
    @NonNull
    public <T> CompletionStage<MutationResult<S, T>> mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter) {
        return delegate.mutateAndGet(mutation, getter)
                       .thenApply(r -> new MutationResult<>(this,r.result()));
    }

    @Override
    @NonNull
    public CompletionStage<MutationResult<S, S>> mutate(@NonNull Mutation<S> mutation) {
        return delegate.mutate(mutation)
                       .thenApply(r -> new MutationResult<>(this,r.result()));

    }

    @Override
    public @NonNull S getRootState() {
        return delegate.getRootState();
    }

    @Override
    @NonNull
    public Subscription addListener(@NonNull IdentityListener<S> listener) {
        return delegate.addListener(listener);
    }

    @Override
    @NonNull
    public Subscription addWeakListener(@NonNull IdentityListener<S> listener) {
        return delegate.addWeakListener(listener);
    }
}
