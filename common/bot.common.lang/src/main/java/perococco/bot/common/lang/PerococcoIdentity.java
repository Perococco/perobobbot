package perococco.bot.common.lang;

import bot.common.lang.*;
import bot.common.lang.fp.Function1;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
public class PerococcoIdentity<S> implements Identity<S> {

    @NonNull
    public static IdentityFactory provider() {
        return new PerococcoIdentityFactory();
    }


    @NonNull
    private volatile S value;

    @NonNull
    private final Updater<S> updater;

    private final Listeners<IdentityListener<S>> listeners = new Listeners<>();

    public PerococcoIdentity(@NonNull S initialValue, @NonNull Updater<S> updater) {
        this.value = initialValue;
        this.updater = updater;
    }

    public PerococcoIdentity(@NonNull S initialValue) {
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
    public @NonNull Subscription addWeakListener(@NonNull IdentityListener<S> listener) {
        final WeakIdentityListener<S> weakIdentityListener = new WeakIdentityListener<>(this,listener);
        return weakIdentityListener.subscription();
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
