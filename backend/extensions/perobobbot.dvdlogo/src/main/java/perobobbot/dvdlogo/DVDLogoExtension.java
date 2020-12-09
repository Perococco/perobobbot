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

    public DVDLogoExtension(@NonNull String userId, @NonNull Overlay overlay) {
        super(NAME);
        this.userId = userId;
        this.overlay = overlay;
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        overlaySubscription.unsubscribe();
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
