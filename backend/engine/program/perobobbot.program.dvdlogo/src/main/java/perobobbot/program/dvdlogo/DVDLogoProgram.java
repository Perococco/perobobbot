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

    /**
     * @param name the name of the program
     * @param overlay the overlay to use to draw the logo
     * @param commandBundleFactory the factory of command bundle
     */
    public DVDLogoProgram(@NonNull String name,
                          @NonNull Overlay overlay,
                          @NonNull CommandBundleFactory<DVDLogoProgram> commandBundleFactory) {
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
