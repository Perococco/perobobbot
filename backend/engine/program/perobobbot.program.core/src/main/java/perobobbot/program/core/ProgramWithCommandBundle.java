package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.common.messaging.CommandBundle;
import perobobbot.common.messaging.CommandBundleFactory;

@RequiredArgsConstructor
public abstract class ProgramWithCommandBundle<P> implements Program {

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final CommandBundleFactory<P> bundleFactory;

    private final SubscriptionHolder commandSubscriptionHolder = new SubscriptionHolder();

    protected abstract P getThis();

    @Override
    @Synchronized
    public void enable() {
        final CommandBundle commandBundle = bundleFactory.create(getThis());
        commandSubscriptionHolder.replaceWith(commandBundle::attachCommandsToChat);
    }

    @Override
    @Synchronized
    public void disable() {
        commandSubscriptionHolder.unsubscribe();
    }

    @Override
    public boolean isEnabled() {
        return commandSubscriptionHolder.hasSubscription();
    }
}
