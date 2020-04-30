package perococco.bot.common.lang;

import bot.common.lang.Disposer;
import bot.common.lang.IdentityListener;
import bot.common.lang.ReadOnlyIdentity;
import bot.common.lang.Subscription;
import lombok.Getter;
import lombok.NonNull;

import java.lang.ref.Reference;

public class WeakIdentityListener<R> implements IdentityListener<R> {

    private static final Disposer DISPOSER = new Disposer();

    @NonNull
    private final Reference<IdentityListener<R>> delegate;

    @NonNull
    @Getter
    private final Subscription subscription;

    public WeakIdentityListener(@NonNull ReadOnlyIdentity<R> identity,
                                @NonNull IdentityListener<R> delegate) {
        this.subscription = identity.addListener(this);
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
