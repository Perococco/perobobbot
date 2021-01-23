package perococco.perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.lang.*;

import java.util.concurrent.CompletionStage;

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
        this(initialValue, new DefaultUpdater<>());
    }

    void start() {
        updater.start();
    }

    void stop() {
        updater.stop();
    }


    @Override
    public @NonNull <T> CompletionStage<T> operate(@NonNull Operator<S, T> operator) {
        return updater.offerUpdate(
                new Update<>(operator, this::getState, this::setState)
        ).thenApply(UpdateResult::getResult);
    }

    @NonNull
    private S getState() {
        return value;
    }

    private void setState(@NonNull S value) {
        final S oldValue = this.value;
        this.value = value;
        if (!oldValue.equals(value)) {
            listeners.warnListeners(l -> l.onValueChange(oldValue, value));
        }
    }

    @Override
    public @NonNull Subscription addListener(@NonNull IdentityListener<S> listener) {
        return listeners.addListener(listener);
    }

    @Override
    public void addWeakListener(@NonNull IdentityListener<S> listener) {
        new WeakIdentityListener<>(this::addListener, listener);
    }

}
