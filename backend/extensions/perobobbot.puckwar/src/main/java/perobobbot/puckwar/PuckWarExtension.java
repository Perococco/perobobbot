package perobobbot.puckwar;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.lang.fp.Function1;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.game.GameOptions;
import perobobbot.puckwar.game.PuckWarGame;

import java.util.Optional;

public class PuckWarExtension extends ExtensionBase {

    public static final String NAME = "puck-war";

    private final @NonNull Overlay overlay;

    private PuckWarGame puckWarGame = null;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public PuckWarExtension(@NonNull Overlay overlay) {
        super(NAME);
        this.overlay = overlay;
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
    public void startGame(@NonNull GameOptions gameOptions) {
        if (!isEnabled() && puckWarGame!=null && puckWarGame.isRunning()) {
            return;
        }
        puckWarGame = new PuckWarGame(overlay.getOverlaySize(), gameOptions);
        puckWarGame.start();
        subscriptionHolder.replaceWith(() -> overlay.addClient(new PuckWarOverlay(puckWarGame)));
    }

    @Synchronized
    public void stopGame() {
        subscriptionHolder.unsubscribe();
        puckWarGame.stop();
        puckWarGame = null;
    }

    @Synchronized
    public void requestStop() {
        getCurrentGame().ifPresent(PuckWarGame::requestStop);
    }

    @Synchronized
    public @NonNull Optional<PuckWarGame> getCurrentGame() {
        return Optional.ofNullable(puckWarGame);
    }


}
