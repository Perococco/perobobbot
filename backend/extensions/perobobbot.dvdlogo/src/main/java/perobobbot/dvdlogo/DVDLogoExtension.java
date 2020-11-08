package perobobbot.dvdlogo;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.extension.ExtensionBase;
import perobobbot.overlay.api.Overlay;

@RequiredArgsConstructor
public class DVDLogoExtension extends ExtensionBase {

    public static final String EXTENSION_NAME = "dvdlogo";

    @Getter
    private final @NonNull String name = EXTENSION_NAME;

    private final @NonNull Overlay overlay;

    private final SubscriptionHolder overlaySubscription = new SubscriptionHolder();

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
