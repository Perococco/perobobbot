package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.extension.ExtensionBase;
import perobobbot.overlay.api.Overlay;

@RequiredArgsConstructor
public class PuckWarExtension extends ExtensionBase {

    public static final String EXTENSION_NAME = "puck-war";

    private final @NonNull Overlay overlay;

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
    public void startGame(int nbPucks, int puckSize) {
        if (subscriptionHolder.hasSubscription() || !isEnabled()) {
            return;
        }
        subscriptionHolder.replaceWith(() -> overlay.addClient(new PuckWarOverlay(nbPucks,puckSize)));
    }

    @Synchronized
    public void stopGame() {
        subscriptionHolder.unsubscribe();
    }
}
