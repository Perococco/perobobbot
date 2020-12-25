package perobobbot.extension;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.rendering.Size;

public abstract class OverlayExtension extends ExtensionBase {

    @Getter(AccessLevel.PROTECTED)
    private final @NonNull Overlay overlay;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public OverlayExtension(@NonNull String name, @NonNull Overlay overlay) {
        super(name);
        this.overlay = overlay;
    }

    protected @NonNull Size getOverlaySize() {
        return overlay.getOverlaySize();
    }

    protected void attachClient(@NonNull OverlayClient overlayClient) {
        subscriptionHolder.replaceWith(() -> overlay.addClient(overlayClient));
    }

    protected void detachClient() {
        subscriptionHolder.unsubscribe();
    }

    protected boolean isClientAttached() {
        return subscriptionHolder.hasSubscription();
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        subscriptionHolder.unsubscribe();
    }
}
