package perococco.bot.common.lang;

import bot.common.lang.*;
import bot.common.lang.fp.Function1;
import lombok.NonNull;
import lombok.Synchronized;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public class PerococcoIdentity<S> implements Identity<S> {

    @NonNull
    private volatile S value;

    private final Listeners<IdentityListener<S>> listeners = new Listeners<>();

    public PerococcoIdentity(@NonNull S initialValue) {
        this.value = initialValue;
    }

    @NonNull
    @Override
    public S getState() {
        return value;
    }

    private void setState(@NonNull S value) {
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
    @Synchronized
    public @NonNull <T> T mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter) {
        final S newState = mutation.mutate(this.getState());
        this.setState(newState);
        return getter.apply(newState);
    }

}
