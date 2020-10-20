package perococco.perobobbot.program.sample.dvdlogo;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.overlay.Overlay;
import perobobbot.service.core.Services;

public class DVDLogoExecutor {

    public static DVDLogoExecutor create(@NonNull Services services) {
        return new DVDLogoExecutor(services.getService(Overlay.class));
    }

    @NonNull
    private final Overlay overlay;

    private final SubscriptionHolder subscription = new SubscriptionHolder();

    private DVDLogoExecutor(Overlay overlay) {
        this.overlay = overlay;
    }

    @Synchronized
    public void startOverlay() {
        if (subscription.hasSubscription()) {
            return;
        }
        this.subscription.replace(overlay.addClient(new DVDLogoOverlay()));
    }

    @Synchronized
    public void stopOverlay() {
        subscription.unsubscribe();
    }
}
