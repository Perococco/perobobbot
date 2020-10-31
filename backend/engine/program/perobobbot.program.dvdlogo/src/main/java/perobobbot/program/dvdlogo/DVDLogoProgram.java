package perobobbot.program.dvdlogo;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.common.messaging.CommandBundleFactory;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.ProgramWithCommandBundle;

public class DVDLogoProgram extends ProgramWithCommandBundle<DVDLogoProgram> {

    private final @NonNull Overlay overlay;

    private final SubscriptionHolder overlaySubscription = new SubscriptionHolder();

    public DVDLogoProgram(@NonNull String name, CommandBundleFactory<DVDLogoProgram> commandBundleFactory, @NonNull Overlay overlay) {
        super(name,commandBundleFactory);
        this.overlay = overlay;
    }

    @Override
    protected DVDLogoProgram getThis() {
        return this;
    }

    @Override
    public void disable() {
        super.disable();
        stopOverlay();
    }

    @Synchronized
    public void startOverlay() {
        if (overlaySubscription.hasSubscription()) {
            return;
        }
        this.overlaySubscription.replaceWith(() -> overlay.addClient(new DVDLogoOverlay()));
    }

    @Synchronized
    public void stopOverlay() {
        overlaySubscription.unsubscribe();
    }
}
