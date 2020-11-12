package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.extension.ExtensionBase;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.game.PuckWarGame;

import java.util.Optional;

@RequiredArgsConstructor
public class PuckWarExtension extends ExtensionBase {

    public static final String EXTENSION_NAME = "puck-war";

    private final @NonNull Overlay overlay;

    private PuckWarGame puckWarGame = null;

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
        puckWarGame = PuckWarGame.create(overlay.getOverlaySize(), puckSize);
        subscriptionHolder.replaceWith(() -> overlay.addClient(new PuckWarOverlay(puckWarGame)));
    }

    @Synchronized
    public void stopGame() {
        subscriptionHolder.unsubscribe();
        puckWarGame = null;
    }

    @Synchronized
    public @NonNull Optional<PuckWarGame> getCurrentGame() {
        return Optional.ofNullable(puckWarGame);
    }


}
