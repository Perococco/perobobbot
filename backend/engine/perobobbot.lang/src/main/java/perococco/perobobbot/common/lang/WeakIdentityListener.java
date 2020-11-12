package perococco.perobobbot.common.lang;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Disposer;
import perobobbot.lang.IdentityListener;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Function1;

import java.lang.ref.Reference;

public class WeakIdentityListener<R> implements IdentityListener<R> {

    private static final Disposer DISPOSER = new Disposer();

    @NonNull
    private final Reference<IdentityListener<R>> delegate;

    @NonNull
    @Getter
    private final Subscription subscription;

    public WeakIdentityListener(@NonNull Function1<? super IdentityListener<R>, ? extends Subscription> listenerAdder,
                                @NonNull IdentityListener<R> delegate) {
        this.subscription = listenerAdder.f(this);
        this.delegate = DISPOSER.add(delegate,subscription::unsubscribe);
    }

    private IdentityListener<R> value() {
        return delegate.get();
    }

    @Override
    public void onValueChange(@NonNull R oldValue, @NonNull R newValue) {
        final IdentityListener<R> listener = delegate.get();
        if (listener != null) {
            listener.onValueChange(oldValue,newValue);
        }
    }
}
