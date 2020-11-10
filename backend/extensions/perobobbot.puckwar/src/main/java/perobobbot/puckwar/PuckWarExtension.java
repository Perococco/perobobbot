package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.common.math.Vector2D;
import perobobbot.extension.ExtensionBase;
import perobobbot.overlay.api.Overlay;

import java.util.Optional;

@RequiredArgsConstructor
public class PuckWarExtension extends ExtensionBase {

    public static final String EXTENSION_NAME = "puck-war";

    private final @NonNull Overlay overlay;

    private PuckWarOverlay puckWarOverlay = null;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    @Override
    public @NonNull String getName() {
        return EXTENSION_NAME;
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        subscriptionHolder.unsubscribe();
    }

    @Synchronized
    public void startGame(int puckSize) {
        if (subscriptionHolder.hasSubscription() || !isEnabled()) {
            return;
        }
        puckWarOverlay = new PuckWarOverlay(puckSize);
        subscriptionHolder.replaceWith(() -> overlay.addClient(puckWarOverlay));
    }

    @Synchronized
    public void stopGame() {
        subscriptionHolder.unsubscribe();
    }

    public void throwPuck(@NonNull Vector2D speed) {
        Optional.ofNullable(puckWarOverlay).ifPresent(o -> o.throwPuck(speed));
    }

    public void setNice(boolean nice) {
        Optional.ofNullable(puckWarOverlay).ifPresent(o -> o.setNice(nice));
    }

}
