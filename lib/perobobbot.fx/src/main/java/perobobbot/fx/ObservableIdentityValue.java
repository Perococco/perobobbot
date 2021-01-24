package perobobbot.fx;

import javafx.beans.value.ObservableValueBase;
import lombok.NonNull;
import perobobbot.lang.IdentityListener;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.atomic.AtomicReference;

public class ObservableIdentityValue<R,S> extends ObservableValueBase<S> implements IdentityListener<R> {

    @NonNull
    private S value;

    private final AtomicReference<S> pending = new AtomicReference<>(null);

    @NonNull
    private final Function1<? super R, ? extends S> getter;

    public ObservableIdentityValue(@NonNull S initialValue, @NonNull Function1<? super R, ? extends S> getter) {
        this.value = initialValue;
        this.getter = getter;
    }

    @Override
    public S getValue() {
        return value;
    }

    @Override
    public void onValueChange(@NonNull R oldRoot, @NonNull R newRoot) {
        final S oldValue = getter.apply(oldRoot);
        final S newValue = getter.apply(newRoot);
        if (oldValue == newValue) {
            return;
        }
        final S pending = this.pending.getAndSet(newValue);
        if (pending == null) {
            javafx.application.Platform.runLater(this::update);
        }
    }

    private void update() {
        this.value = this.pending.getAndSet(null);
        fireValueChangedEvent();
    }
}
