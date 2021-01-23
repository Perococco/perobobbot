package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.lang.*;

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
    public <T> @NonNull T operate(@NonNull Operator<S, T> operator) {
        final S oldState = this.getState();
        final S newState = operator.mutate(oldState);
        this.setState(newState);
        return operator.getValue(oldState, newState);
    }

}
