package perococco.bot.common.lang;

import bot.common.lang.*;
import bot.common.lang.fp.Function1;
import lombok.NonNull;

import java.util.concurrent.*;

/**
 * @author perococco
 **/
public class PerococcoAsyncIdentity<S> implements AsyncIdentity<S> {

    @NonNull
    public static AsyncIdentityFactory provider() {
        return new PerococcoAsyncIdentityFactory();
    }


    @NonNull
    private volatile S value;

    @NonNull
    private final Updater<S> updater;

    private final Listeners<IdentityListener<S>> listeners = new Listeners<>();

    public PerococcoAsyncIdentity(@NonNull S initialValue, @NonNull Updater<S> updater) {
        this.value = initialValue;
        this.updater = updater;
    }

    public PerococcoAsyncIdentity(@NonNull S initialValue) {
        this(initialValue,new DefaultUpdater<>());
    }

    void start() {
        updater.start();
    }

    void stop() {
        updater.stop();
    }

    @NonNull
    @Override
    public S getRootState() {
        return value;
    }

    private void setRootState(@NonNull S value) {
        final S oldValue = this.value;
        this.value = value;
        if (oldValue != value) {
            listeners.warnListeners(l -> l.onValueChange(oldValue, value));
        }
    }

    @Override
    public @NonNull Subscription addListener(@NonNull IdentityListener<S> listener) {
        return listeners.addListener(listener);
    }

    @Override
    public void addWeakListener(@NonNull IdentityListener<S> listener) {
        new WeakIdentityListener<>(this::addListener,listener);
    }

    @Override
    public @NonNull <T> CompletionStage<MutationResult<S, T>> mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter) {
        return updater.<T>offerUpdatingOperation(
                mutation,
                this::getRootState,
                this::setRootState,
                getter
        ).thenApply(this::createMutation);
    }

    @NonNull
    private <T> MutationResult<S,T> createMutation(UpdateResult<S, T> updateResult) {
        return new MutationResult<>(this,updateResult.result());
    }

}
