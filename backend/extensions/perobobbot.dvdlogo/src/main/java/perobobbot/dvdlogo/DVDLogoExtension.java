package perobobbot.dvdlogo;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.lang.fp.Function1;
import perobobbot.overlay.api.Overlay;

public class DVDLogoExtension extends ExtensionBase {

    public static final String NAME = "dvdlogo";

    private final @NonNull String userId;

    private final @NonNull Overlay overlay;

    private final SubscriptionHolder overlaySubscription = new SubscriptionHolder();

    private final CommandBundleLifeCycle commandBundleLifeCycle;

    public DVDLogoExtension(@NonNull String userId, @NonNull Overlay overlay,
                            @NonNull Function1<? super DVDLogoExtension, ? extends CommandBundleLifeCycle> lifeCycleFactory) {
        super(NAME);
        this.userId = userId;
        this.overlay = overlay;
        this.commandBundleLifeCycle = lifeCycleFactory.f(this);
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }

    @Override
    protected void onEnabled() {
        super.onEnabled();
        commandBundleLifeCycle.attachCommandBundle();
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        overlaySubscription.unsubscribe();
        commandBundleLifeCycle.detachCommandBundle();
    }

    @Synchronized
    public void startOverlay() {
        if (isEnabled()) {
            if (overlaySubscription.hasSubscription()) {
                return;
            }
            this.overlaySubscription.replaceWith(() -> overlay.addClient(new DVDLogoOverlay()));
        }
    }

    @Synchronized
    public void stopOverlay() {
        if (isEnabled()) {
            overlaySubscription.unsubscribe();
        }
    }
}
